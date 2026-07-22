package com.rostrumpodcast.rostrum.ui.vm

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rostrumpodcast.rostrum.api.apple.ApplePodcastClient
import com.rostrumpodcast.rostrum.api.db.AppDatabase
import com.rostrumpodcast.rostrum.api.db.model.PodcastModel
import com.rostrumpodcast.rostrum.api.db.model.PodcastSubscriptionModel
import com.rostrumpodcast.rostrum.api.model.PodcastPreviewModel
import com.rostrumpodcast.rostrum.manager.AddPodcastResult
import com.rostrumpodcast.rostrum.manager.PodcastManager
import coil3.compose.AsyncImagePainter
import coil3.toBitmap
import com.materialkolor.ktx.themeColorOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PodcastSettingsViewModel : ViewModel() {

    fun toggleSubscriptionAutoDownload(
        db: AppDatabase,
        subscription: PodcastSubscriptionModel,
        enable: Boolean
    ) {
        viewModelScope.launch {
            if(enable) {
                db.podcastSubscriptions().enableAutoDownload(subscription.origin)
            } else {
                db.podcastSubscriptions().disableAutoDownload(subscription.origin)
            }
        }
    }

    fun toggleSubscriptionNotifications(
        db: AppDatabase,
        subscription: PodcastSubscriptionModel,
        enable: Boolean
    ) {
        viewModelScope.launch {
            if(enable) {
                db.podcastSubscriptions().enableNotifications(subscription.origin)
            } else {
                db.podcastSubscriptions().disableNotifications(subscription.origin)
            }
        }
    }

    fun setSkipBeginning(db: AppDatabase, podcast: PodcastModel, value: Int) {
        viewModelScope.launch {
            db.podcasts().setSkipBeginning(podcast.origin, value)
        }
    }

    fun setSkipEnding(db: AppDatabase, podcast: PodcastModel, value: Int) {
        viewModelScope.launch {
            db.podcasts().setSkipEnding(podcast.origin, value)
        }
    }

    fun setOverrideTitle(db: AppDatabase, podcast: PodcastModel, overrideTitle: String) {
        viewModelScope.launch {
            db.podcasts().setOverrideTitle(podcast.origin, overrideTitle)
        }
    }

}