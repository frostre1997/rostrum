package app.rostrumpodcast.rostrum.background.work

import android.content.Context
import app.rostrumpodcast.rostrum.SettingsRepository
import app.rostrumpodcast.rostrum.api.db.AppDatabase
import app.rostrumpodcast.rostrum.manager.DownloadManager
import kotlinx.coroutines.flow.first

class DeleteOldDownloadsWork(
    val context: Context,
    val db: AppDatabase,
    val settingsRepository: SettingsRepository = SettingsRepository(context)
) {

    suspend fun doWork(): Boolean {
        val afterSeconds = settingsRepository.behavior.deleteDownloadsAfterSeconds.first()
        if(afterSeconds == -1) return true

        val bundles = db.podcastEpisodeDownloads()
            .getOlderThanSync(System.currentTimeMillis() - (afterSeconds * 1000L))

        for(bundle in bundles) {
            DownloadManager.deleteEpisodeDownload(
                context = context,
                db = db,
                episode = bundle.episode
            )
        }

        return true
    }

}