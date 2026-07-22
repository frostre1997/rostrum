package com.rostrumpodcast.rostrum.api.sync.model.result

import com.rostrumpodcast.rostrum.api.sync.model.episodeactions.EpisodeAction
import kotlinx.serialization.Serializable

@Serializable
data class EpisodeActionsGetResult(
    val actions: List<EpisodeAction>,
    val timestamp: Long
)