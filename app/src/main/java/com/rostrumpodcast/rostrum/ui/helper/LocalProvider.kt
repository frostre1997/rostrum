package app.podiumpodcast.podium.ui.helper

import androidx.compose.runtime.compositionLocalOf
import app.podiumpodcast.podium.SettingsRepository
import app.podiumpodcast.podium.api.db.AppDatabase

val LocalDatabase = compositionLocalOf<AppDatabase> { null!! }
val LocalSettingsRepository = compositionLocalOf<SettingsRepository> { null!! }