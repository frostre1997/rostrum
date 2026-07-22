package com.rostrumpodcast.rostrum.api.sync.gpodder

import android.os.Build
import com.rostrumpodcast.rostrum.BuildConfig
import com.rostrumpodcast.rostrum.api.sync.SyncClient
import com.rostrumpodcast.rostrum.api.sync.gpodder.route.Auth
import com.rostrumpodcast.rostrum.api.sync.gpodder.route.Device
import com.rostrumpodcast.rostrum.api.sync.gpodder.route.EpisodeActions
import com.rostrumpodcast.rostrum.api.sync.gpodder.route.Subscriptions
import com.rostrumpodcast.rostrum.api.sync.model.result.SyncResult
import com.rostrumpodcast.rostrum.utils.json
import com.google.common.net.HttpHeaders
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.AcceptAllCookiesStorage
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json

class GpodderClient(
    val deviceCaption: String,
    val deviceId: String,

    val baseUrl: String = "https://gpodder.net",
    val username: String,
    val password: String,
    val cookie: String
) : SyncClient {

    val httpClient = HttpClient {
        followRedirects = true

        install(DefaultRequest) {
            val appVersion = BuildConfig.VERSION_CODE

            val os = "Android ${Build.VERSION.RELEASE}"
            val model = Build.MODEL

            val github = "https://github.com/aimok04/rostrum"

            header(HttpHeaders.USER_AGENT, "rostrum/$appVersion ($os; $model; +$github)")
            header(HttpHeaders.COOKIE, cookie)
        }

        install(HttpCookies) {
            storage = AcceptAllCookiesStorage()
        }

        install(ContentNegotiation) {
            json(json = json)
        }
    }

    val auth = Auth(this)
    val device = Device(this)
    val episodeActions = EpisodeActions(this)
    val subscriptions = Subscriptions(this)

    suspend fun <T> parseResponse(
        response: HttpResponse,
        parseResult: suspend () -> T = { Unit as T }
    ): SyncResult.Success<T> {
        if(response.status == HttpStatusCode.Unauthorized || response.status == HttpStatusCode.Forbidden)
            throw SyncResult.Unauthenticated(response)

        if(response.status != HttpStatusCode.OK)
            throw SyncResult.Failure(response)

        return SyncResult.Success(
            result = parseResult()
        )
    }

}