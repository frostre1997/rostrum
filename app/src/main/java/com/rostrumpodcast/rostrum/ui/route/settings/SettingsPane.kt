package com.rostrumpodcast.rostrum.ui.route.settings

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.rostrumpodcast.rostrum.ui.DetailPaneKey
import com.rostrumpodcast.rostrum.ui.component.common.BackButton
import com.rostrumpodcast.rostrum.ui.navigation.OpmlImporting
import com.rostrumpodcast.rostrum.ui.navigation.Restore
import com.rostrumpodcast.rostrum.ui.route.settings.pane.SettingsAppearanceKey
import com.rostrumpodcast.rostrum.ui.route.settings.pane.SettingsAppearancePane
import com.rostrumpodcast.rostrum.ui.route.settings.pane.SettingsBackgroundActivityKey
import com.rostrumpodcast.rostrum.ui.route.settings.pane.SettingsBackgroundActivityPane
import com.rostrumpodcast.rostrum.ui.route.settings.pane.SettingsDatabaseKey
import com.rostrumpodcast.rostrum.ui.route.settings.pane.SettingsDatabasePane
import com.rostrumpodcast.rostrum.ui.route.settings.pane.SettingsDebugKey
import com.rostrumpodcast.rostrum.ui.route.settings.pane.SettingsDebugPane
import com.rostrumpodcast.rostrum.ui.route.settings.pane.SettingsDownloadsAndStorageKey
import com.rostrumpodcast.rostrum.ui.route.settings.pane.SettingsDownloadsAndStoragePane
import com.rostrumpodcast.rostrum.ui.route.settings.pane.SettingsPlaybackKey
import com.rostrumpodcast.rostrum.ui.route.settings.pane.SettingsPlaybackPane
import com.rostrumpodcast.rostrum.ui.route.settings.pane.SettingsPrivacyKey
import com.rostrumpodcast.rostrum.ui.route.settings.pane.SettingsPrivacyPane
import com.rostrumpodcast.rostrum.ui.route.settings.pane.SettingsSynchronizationKey
import com.rostrumpodcast.rostrum.ui.route.settings.pane.SettingsSynchronizationPane
import kotlinx.serialization.Serializable

@SuppressLint("ParcelCreator")
@Serializable
open class SettingsPaneKey : DetailPaneKey()

@Composable
fun SettingsPane(
    showBackButton: Boolean,

    contentKey: SettingsPaneKey,
    backStack: NavBackStack<NavKey>,

    onClose: () -> Unit
) {
    BackHandler {
        onClose()
    }

    @Composable
    fun NavigationIcon() {
        if(showBackButton) BackButton {
            onClose()
        }
    }

    when(contentKey) {
        is SettingsDatabaseKey -> {
            SettingsDatabasePane(
                navigationIcon = {
                    NavigationIcon()
                },

                onRestore = {
                    onClose()
                    backStack.add(Restore)
                },
                onOpmlImport = {
                    onClose()
                    backStack.add(OpmlImporting)
                }
            )
        }

        is SettingsAppearanceKey -> {
            SettingsAppearancePane(
                navigationIcon = {
                    NavigationIcon()
                }
            )
        }

        is SettingsPlaybackKey -> {
            SettingsPlaybackPane(
                navigationIcon = {
                    NavigationIcon()
                }
            )
        }

        is SettingsBackgroundActivityKey -> {
            SettingsBackgroundActivityPane(
                navigationIcon = {
                    NavigationIcon()
                }
            )
        }

        is SettingsDownloadsAndStorageKey -> {
            SettingsDownloadsAndStoragePane(
                navigationIcon = {
                    NavigationIcon()
                }
            )
        }

        is SettingsSynchronizationKey -> {
            SettingsSynchronizationPane(
                navigationIcon = {
                    NavigationIcon()
                }
            )
        }

        is SettingsPrivacyKey -> {
            SettingsPrivacyPane(
                navigationIcon = {
                    NavigationIcon()
                }
            )
        }

        is SettingsDebugKey -> {
            SettingsDebugPane(
                navigationIcon = {
                    NavigationIcon()
                }
            )
        }

        else -> throw Exception("Unknown instance of SettingsPaneKey")
    }
}