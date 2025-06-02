package ru.normno.rutubedownloader.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    state: HomeState,
    setVideoUrl: (String) -> Unit,
    onGetVideo: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            TextField(
                value = state.videoUrlWithId,
                onValueChange = setVideoUrl,
                label = {
                    Text(
                        text = "Video URL",
                    )
                }
            )
        }
        Button(
            onClick = onGetVideo,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
        ) {
            Text(
                text = "Get Video"
            )
        }
        Text(
            text = state.videoUrlM3U8?.title ?: "",
        )
    }
}