package com.rostrumpodcast.rostrum.api.apple.route

import com.rostrumpodcast.rostrum.api.apple.ApplePodcastClient
import com.rostrumpodcast.rostrum.api.apple.model.top.LookupResponse
import com.rostrumpodcast.rostrum.utils.json
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