package app.rostrumpodcast.rostrum.api.apple.route

import app.rostrumpodcast.rostrum.api.apple.ApplePodcastClient
import app.rostrumpodcast.rostrum.api.apple.model.Genre
import app.rostrumpodcast.rostrum.api.apple.model.top.ToppodcastResponse
import app.rostrumpodcast.rostrum.api.model.PodcastPreviewModel
import app.rostrumpodcast.rostrum.utils.json
import io.ktor.client.call.body
import io.ktor.client.request.get

class Toppodcast(
    val client: ApplePodcastClient
) : ApiRoute(client) {

    suspend fun load(
        countryCode: String,
        limit: Int = 50,
        genre: Genre? = null
    ): List<PodcastPreviewModel> {
        val genreStr = genre?.let { "genre=${it.id}/" } ?: ""

        val body =
            client.httpClient.get("https://itunes.apple.com/$countryCode/rss/toppodcast/limit=$limit/${genreStr}explicit=true/json")
                .body<String>()

        val response = json.decodeFromString<ToppodcastResponse>(body)
        return response.feed.entry.mapNotNull { it.toPodcastPreview() }
    }

}