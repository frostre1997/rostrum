package com.rostrumpodcast.rostrum.ui.dialog.bottomsheet

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.rostrumpodcast.rostrum.api.db.model.PodcastModel
import com.rostrumpodcast.rostrum.ui.view.model.podcastettingsView
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun podcastettingsBottomSheet(
    onDismiss: () -> Unit,
    podcast: PodcastModel
) {
    val scope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },

        sheetState = sheetState,
        contentWindowInsets = { WindowInsets() },
        dragHandle = null,
    ) {
        podcastettingsView(
            podcast = podcast,
            onBack = {
                scope.launch {
                    sheetState.hide()
                    onDismiss()
                }
            }
        )
    }
}