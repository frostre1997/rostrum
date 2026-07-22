package app.rostrumpodcast.rostrum.api.apple

import app.rostrumpodcast.rostrum.api.apple.route.Lookup
import app.rostrumpodcast.rostrum.api.apple.route.Search
import app.rostrumpodcast.rostrum.api.apple.route.Toppodcast
import app.rostrumpodcast.rostrum.utils.json
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

class ApplePodcastClient {

    val httpClient = HttpClient {
        followRedirects = true

        install(ContentNegotiation) {
            json(json = json)
        }
    }

    val lookup = Lookup(this)
    val search = Search(this)
    val toppodcast = Toppodcast(this)

}