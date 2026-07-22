package app.rostrumpodcast.rostrum.background.work

import android.content.Context
import app.rostrumpodcast.rostrum.SettingsRepository
import app.rostrumpodcast.rostrum.api.db.AppDatabase
import app.rostrumpodcast.rostrum.manager.DownloadManager
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