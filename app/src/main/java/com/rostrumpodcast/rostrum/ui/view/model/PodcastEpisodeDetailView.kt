package com.rostrumpodcast.rostrum.ui.view.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme
import com.rostrumpodcast.rostrum.api.db.model.PodcastEpisodeBundle
import com.rostrumpodcast.rostrum.api.db.model.PodcastModel

@Composable
fun PodcastEpisodeDetailView(
    bundle: PodcastEpisodeBundle,
    parent: PodcastModel?,
    showParentLink: Boolean = false,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    onShowParent: () -> Unit = { },
    onBack: () -> Unit
) {
    // TODO: implement episode detail
}
