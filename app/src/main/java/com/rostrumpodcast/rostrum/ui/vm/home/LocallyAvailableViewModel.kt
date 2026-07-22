package com.rostrumpodcast.rostrum.ui.vm.home

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.rostrumpodcast.rostrum.api.db.AppDatabase

class LocallyAvailableViewModel(
    val db: AppDatabase
) : ViewModel() {

    val lazyGridState = LazyGridState()

    val locallyAvailable = Pager(
        PagingConfig(
            pageSize = 15
        )
    ) {
        db.podcasts().all()
    }.flow

}