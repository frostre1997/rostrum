package com.rostrumpodcast.rostrum

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rostrumpodcast.rostrum.ui.DeepLink
import com.rostrumpodcast.rostrum.ui.Main
import com.rostrumpodcast.rostrum.ui.navigation.Home
import com.rostrumpodcast.rostrum.ui.theme.RostrumTheme

@Composable
fun RostrumApp(
    deepLink: DeepLink?
) {
    RostrumTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Main(deepLink)
        }
    }
}