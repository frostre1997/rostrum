package app.rostrumpodcast.rostrum.api.apple.route

import app.rostrumpodcast.rostrum.api.apple.ApplePodcastClient
import app.rostrumpodcast.rostrum.api.apple.model.top.LookupResponse
import app.rostrumpodcast.rostrum.utils.json
import io.ktor.client.call.body
import io.ktor.client.request.get

class Lookup(
    val client: ApplePodcastClient
) : ApiRoute(client) {

    suspend fun getRssFeedUrl(
        id: String
    ): String {
        val body = client.httpClient.get("https://itunes.apple.com/lookup?id=$id")
            .body<String>()

        val response = json.decodeFromString<LookupResponse>(body)
        return response.results.first().feedUrl
    }

}