package com.rostrumpodcast.rostrum.ui.view.model

import androidx.compose.runtime.Composable
import com.rostrumpodcast.rostrum.api.db.model.PodcastEpisodeModel
import com.rostrumpodcast.rostrum.api.db.model.PodcastModel

@Composable
fun PodcastDetailView(
    podcast: PodcastModel,
    onBack: () -> Unit,
    onClickEpisode: (PodcastEpisodeModel) -> Unit
) {
    // TODO: implement podcast detail
}
