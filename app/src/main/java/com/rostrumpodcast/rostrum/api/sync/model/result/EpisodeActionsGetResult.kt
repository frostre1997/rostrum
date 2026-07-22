package app.rostrumpodcast.rostrum.api.sync.model.result

import app.rostrumpodcast.rostrum.api.sync.model.episodeactions.EpisodeAction
import kotlinx.serialization.Serializable

@Serializable
data class EpisodeActionsGetResult(
    val actions: List<EpisodeAction>,
    val timestamp: Long
)