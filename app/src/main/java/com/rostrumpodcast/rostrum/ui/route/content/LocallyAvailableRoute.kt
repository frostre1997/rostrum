package com.rostrumit.rostrum.ui.route.content

import com.rostrumit.rostrum.api.db.model.PodcastEpisodeModel
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.rostrumit.rostrum.R
import com.rostrumit.rostrum.ui.component.common.BackButton
import com.rostrumit.rostrum.ui.component.media.FloatingMediaPlayerSpacer
import com.rostrumit.rostrum.ui.component.model.PodcastCard
import com.rostrumit.rostrum.ui.helper.LocalDatabase
import com.rostrumit.rostrum.ui.vm.home.LocallyAvailableViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LocallyAvailableRoute(
    onClickPodcast: (origin: String) -> Unit,
    onBack: () -> Unit
) {
    val db = LocalDatabase.current
    val vm = viewModel { LocallyAvailableViewModel(db) }

    val locallyAvailable = vm.locallyAvailable.collectAsLazyPagingItems<PodcastEpisodeModel>()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    BackButton { onBack() }
                },
                title = {
                    Text(stringResource(R.string.route_locally_available))
                }
            )
        }
    ) { inset ->
        LazyVerticalGrid(
            state = when(locallyAvailable.itemCount) {
                0 -> LazyGridState()
                else -> vm.lazyGridState
            },
            columns = GridCells.Adaptive(100.dp),
            modifier = Modifier.padding(inset),
            contentPadding = PaddingValues(16.dp),
        ) {
            items(locallyAvailable.itemCount) {
                val item = locallyAvailable[it] ?: return@items

                PodcastCard(
                    it = item,
                    onClick = { onClickPodcast(item.it.origin) }
                )
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                FloatingMediaPlayerSpacer()
            }
        }
    }
}
