package com.rostrumpodcast.rostrum.utils.rss

import app.rostrumpodcast.rostrum.api.db.model.PodcastEpisodeModel
import app.rostrumpodcast.rostrum.api.db.model.PodcastModel
import app.rostrumpodcast.rostrum.ui.parseItunesDuration
import app.rostrumpodcast.rostrum.ui.parsePubDate
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssItem

fun RssChannel.toPodcast(
    origin: String,
    fileSize: Long,
    oldPodcast: PodcastModel?
): PodcastModel {
    return PodcastModel(
        origin = origin,
        link = link ?: "",
        title = title ?: "",
        description = description ?: "",
        author = itunesChannelData?.author ?: "",
        imageUrl = image?.url ?: "",
        imageSeedColor = oldPodcast?.imageSeedColor ?: 0,
        languageCode = "unknown",
        fileSize = fileSize,

        overrideTitle = oldPodcast?.overrideTitle ?: "",
        skipBeginning = oldPodcast?.skipBeginning ?: 0,
        skipEnding = oldPodcast?.skipEnding ?: 0
    )
}

fun RssItem.toPodcastEpisode(
    podcast: PodcastModel,
    new: Boolean = false
): PodcastEpisodeModel {
    return PodcastEpisodeModel(
        id = "${podcast.origin}:$guid",
        guid = guid ?: "",
        origin = podcast.origin,
        link = link ?: "",
        title = title ?: "",
        description = description ?: "",
        imageUrl = (itunesItemData?.image ?: image ?: "").ifBlank { podcast.imageUrl },
        author = author ?: "",
        pubDate = parsePubDate(pubDate ?: ""),
        duration = itunesItemData?.duration?.let {
            parseItunesDuration(it)
        } ?: -1,
        audioUrl = audio ?: video ?: "",
        podcastTitle = podcast.title,
        imageSeedColor = podcast.imageSeedColor,
        new = new
    )
}
