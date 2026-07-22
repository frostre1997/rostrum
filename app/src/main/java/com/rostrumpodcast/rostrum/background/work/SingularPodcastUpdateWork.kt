package com.rostrumpodcast.rostrum.background.work

import android.content.Context
import com.rostrumpodcast.rostrum.api.db.AppDatabase
import com.rostrumpodcast.rostrum.api.db.model.PodcastModel
import com.rostrumpodcast.rostrum.api.rss.FetchPodcastClient
import com.rostrumpodcast.rostrum.api.rss.FetchPodcastClientResult
import com.rostrumpodcast.rostrum.manager.DownloadManager
import com.rostrumpodcast.rostrum.utils.rss.toPodcast
import com.rostrumpodcast.rostrum.utils.rss.toPodcastEpisode

class SingularPodcastUpdateWork(
    val context: Context,
    val db: AppDatabase
) {

    private val fetchPodcastClient = FetchPodcastClient()

    suspend fun doWork(
        oldPodcast: PodcastModel
    ) {
        val episodeIds = db.podcastEpisodes().getEpisodeIds(oldPodcast.origin)

        val response = fetchPodcastClient.fetchNoCache(oldPodcast.origin)

        if(response !is FetchPodcastClientResult.Success)
            throw Exception(response.toString())

        val podcast =
            response.rssChannel.toPodcast(oldPodcast.origin, response.fileSize, oldPodcast)
        db.podcast().update(podcast)

        val newEpisodes = response.rssChannel.items
            .filter { !episodeIds.contains("${podcast.origin}:${it.guid}") }
            .map { it.toPodcastEpisode(podcast = podcast, new = true) }

        db.podcastEpisodes()
            .insertAllAndUpdateNewEpisodesCount(
                podcast.origin, *newEpisodes.toTypedArray()
            )
        newEpisodes.forEach {
            db.podcastEpisodePlayStates().initState(it.id)
        }

        val subscription = db.podcastubscriptions().getSync(podcast.origin)
        if(subscription?.enableAutoDownload == true) newEpisodes.forEach {
            try {
                DownloadManager.downloadEpisode(context, db, it.id)
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }

        db.podcastubscriptions()
            .logUpdate(origin = podcast.origin)
    }

}