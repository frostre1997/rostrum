package app.rostrumpodcast.rostrum.background.work

import android.content.Context
import app.rostrumpodcast.rostrum.SettingsRepository
import app.rostrumpodcast.rostrum.api.db.AppDatabase
import app.rostrumpodcast.rostrum.api.rss.FetchPodcastClient
import app.rostrumpodcast.rostrum.api.rss.FetchPodcastClientResult
import app.rostrumpodcast.rostrum.background.notification.DebugUpdateNotification
import app.rostrumpodcast.rostrum.background.notification.NewPodcastEpisodeNotification
import app.rostrumpodcast.rostrum.manager.DownloadManager
import app.rostrumpodcast.rostrum.utils.rss.toPodcastEpisode
import coil3.Bitmap
import coil3.ImageLoader
import coil3.request.ErrorResult
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.request.allowHardware
import coil3.toBitmap
import com.prof18.rssparser.model.RssChannel
import kotlinx.coroutines.flow.first

const val HEADER_OVERHEAD = 1024L

class PodcastUpdateWork(
    val context: Context,
    val db: AppDatabase,
    val settingsRepository: SettingsRepository = SettingsRepository(context)
) {

    val fetchPodcastClient = FetchPodcastClient()

    var imageBitmapMap = mutableMapOf<String, Bitmap>()

    suspend fun doWork() {
        val enableDebugUpdateNotification =
            settingsRepository.debug.enableUpdateNotification.first()

        val subscriptions = db.podcastubscriptions().allSortedByLastUpdate()

        var cachedpodcast = 0
        var dataUsage = 0L

        for(subscription in subscriptions) {
            val origin = subscription.podcast.origin

            val response = fetchPodcastClient.fetch(
                origin = origin,
                lastModified = subscription.subscription.cacheLastModified,
                eTag = subscription.subscription.cacheETag,
                contentLength = subscription.subscription.cacheContentLength
            )

            if(enableDebugUpdateNotification) {
                DebugUpdateNotification(subscription, response)
                    .send(context)
            }

            when(response) {
                is FetchPodcastClientResult.Success -> {
                    dataUsage += response.fileSize + HEADER_OVERHEAD

                    val episodeIds = db.podcastEpisodes().getEpisodeIds(origin)
                    val podcast = subscription.podcast

                    val newEpisodes = response.rssChannel.items
                        .filter { !episodeIds.contains("$origin:${it.guid}") }
                        .map { it.toPodcastEpisode(podcast = podcast, new = true) }

                    if(subscription.subscription.enableNotifications) for(episode in newEpisodes) NewPodcastEpisodeNotification(
                        podcastTitle = podcast.fetchTitle(),
                        episode = episode,
                        imageBitmap = loadBitmap(response.rssChannel)
                    ).send(context)

                    db.podcastEpisodes()
                        .insertAllAndUpdateNewEpisodesCount(
                            podcast.origin, *newEpisodes.toTypedArray()
                        )
                    newEpisodes.forEach {
                        db.podcastEpisodePlayStates().initState(it.id)
                    }

                    if(subscription.subscription.enableAutoDownload) newEpisodes.forEach {
                        try {
                            DownloadManager.downloadEpisode(
                                context = context,
                                db = db,
                                episodeId = it.id,
                                auto = true
                            )
                        } catch(e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    db.podcastubscriptions()
                        .logUpdate(origin = origin)
                    db.podcastubscriptions()
                        .storeCacheValues(
                            origin = origin,
                            lastModified = response.lastModified,
                            eTag = response.eTag,
                            contentLength = response.contentLength
                        )
                }

                is FetchPodcastClientResult.Unchanged -> {
                    dataUsage += HEADER_OVERHEAD
                    cachedpodcast += 1

                    db.podcastubscriptions()
                        .logUpdate(origin = origin)
                }

                is FetchPodcastClientResult.Failure -> {
                    dataUsage += HEADER_OVERHEAD
                    // TODO: do something better
                }
            }
        }

        // don't log run when only a few podcast are cached
        // because large requests can mess up avg
        if(cachedpodcast >= 2) {
            db.statisticsUpdatePodcastRun()
                .log(dataUsage)
        }
    }

    suspend fun loadBitmap(rssChannel: RssChannel): Bitmap? {
        if(rssChannel.image?.url == null) return null

        if(imageBitmapMap.contains(rssChannel.image!!.url))
            return imageBitmapMap[rssChannel.image!!.url]

        val loader = ImageLoader(context)

        val request = ImageRequest.Builder(context)
            .data(rssChannel.image!!.url)
            .allowHardware(false)
            .build()

        val result = loader.execute(request)
        if(result is ErrorResult) {
            result.throwable.printStackTrace()
            return null
        } else if(result is SuccessResult) {
            val bitmap = result.image.toBitmap()
            imageBitmapMap[rssChannel.image!!.url!!] = bitmap
            return bitmap
        }

        return null
    }

}