package com.rostrumpodcast.rostrum.ui.component.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rostrumpodcast.rostrum.R

@Composable
fun PoweredByApplePodcastsBadge() {
    Row {
        Icon(
            painter = rememberVectorPainter(image = R.drawable.ic_apple_podcasts),
            contentDescription = "Apple Podcasts",
            modifier = Modifier.size(16.dp)
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = "Apple Podcasts",
            style = MaterialTheme.typography.labelSmall
        )
    }
}
