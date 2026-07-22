package app.rostrumpodcast.rostrum.api.apple.route

import android.net.Uri
import app.rostrumpodcast.rostrum.api.apple.ApplePodcastClient
import app.rostrumpodcast.rostrum.api.apple.model.top.SearchResponse
import app.rostrumpodcast.rostrum.api.model.PodcastPreviewModel
import app.rostrumpodcast.rostrum.utils.json
import io.ktor.client.call.body
import io.ktor.client.request.get

class Search(
    val client: ApplePodcastClient
) : ApiRoute(client) {

    suspend fun search(
        query: String,
        countryCode: String
    ): List<PodcastPreviewModel> {
        val url = Uri.parse("https://itunes.apple.com/search?media=podcast")
            .buildUpon()
            .appendQueryParameter("country", countryCode)
            .appendQueryParameter("term", query)
            .build()

        val body = client.httpClient.get(url.toString()).body<String>()

        val response = json.decodeFromString<SearchResponse>(body)
        return response.results.mapNotNull { it.toPodcastPreview() }
    }

}