package com.rostrumpodcast.rostrum.ui.helper

import androidx.compose.runtime.compositionLocalOf
import com.rostrumpodcast.rostrum.SettingsRepository
import com.rostrumpodcast.rostrum.api.db.AppDatabase

val LocalDatabase = compositionLocalOf<AppDatabase> { null!! }
val LocalSettingsRepository = compositionLocalOf<SettingsRepository> { null!! }