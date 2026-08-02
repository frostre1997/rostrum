package com.rostrumpodcast.rostrum.ui.route.home

import androidx.compose.runtime.Composable
import com.rostrumpodcast.rostrum.api.db.model.PodcastEpisodeModel

@Composable
fun HomeRoute(
    onSettings: () -> Unit,
    onClickSubscriptions: () -> Unit,
    onClickContinuePlaying: () -> Unit,
    onClickNewEpisodes: () -> Unit,
    onClickLocallyAvailable: () -> Unit,
    onClickAddPodcast: () -> Unit,
    onClickDiscover: () -> Unit,
    onClickPodcast: (String) -> Unit,
    onClickEpisode: (PodcastEpisodeModel) -> Unit
) {
    // TODO: implement home
}
