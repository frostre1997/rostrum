package com.rostrumpodcast.rostrum.api.sync.model.result

import kotlinx.serialization.Serializable

@Serializable
data class AuthResult(
    val cookie: String
)