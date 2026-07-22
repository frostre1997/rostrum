package com.rostrumpodcast.rostrum.api.sync.gpodder.route

import com.rostrumpodcast.rostrum.api.sync.gpodder.GpodderClient
import com.rostrumpodcast.rostrum.api.sync.model.result.AuthResult
import com.rostrumpodcast.rostrum.api.sync.model.result.SyncResult
import io.ktor.client.request.basicAuth
import io.ktor.client.request.post
import io.ktor.http.HttpHeaders
import io.ktor.http.path
import io.ktor.http.takeFrom

class Auth(
    val client: GpodderClient
) : ApiRoute(client) {

    /**
     * Login to gpodder.net
     *
     * @return whether login was successful
     */
    suspend fun login(): SyncResult.Success<AuthResult> {
        val response = client.httpClient.post {
            url {
                takeFrom(client.baseUrl)
                path("api", "2", "auth", client.username, "login.json")
            }

            basicAuth(client.username, client.password)
        }

        return client.parseResponse(
            response = response
        ) {
            AuthResult(
                cookie = response.headers[HttpHeaders.SetCookie] ?: ""
            )
        }
    }

}