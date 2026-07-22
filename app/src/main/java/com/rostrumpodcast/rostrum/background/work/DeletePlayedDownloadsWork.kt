package com.rostrumpodcast.rostrum.background.work

import android.content.Context
import com.rostrumpodcast.rostrum.SettingsRepository
import com.rostrumpodcast.rostrum.api.db.AppDatabase
import com.rostrumpodcast.rostrum.manager.DownloadManager
import kotlinx.coroutines.flow.first

class DeletePlayedDownloadsWork(
    val context: Context,
    val db: AppDatabase,
    val settingsRepository: SettingsRepository = SettingsRepository(context)
) {

    suspend fun doWork(): Boolean {
        if(settingsRepository.behavior.deletePlayedDownloads.first()) {
            val bundles = db.podcastEpisodeDownloads().allPlayedByTimestamp()

            for(bundle in bundles) {
                if(!bundle.playState.played) continue

                DownloadManager.deleteEpisodeDownload(
                    context = context,
                    db = db,
                    episode = bundle.episode
                )
            }
        }

        return true
    }

}