@file:OptIn(ExperimentalTime::class)

package ru.normno.rutubedownloader.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.normno.rutubedownloader.util.dowload.Progress.formatSpeed
import ru.normno.rutubedownloader.util.video.ParseM3U8Playlist.VideoQuality
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
fun HomeScreen(
    state: HomeState,
    setVideoUrl: (String) -> Unit,
    onSelectedVideoQuality: (VideoQuality) -> Unit,
    onDownloadVideo: () -> Unit,
    onGetVideo: () -> Unit,
) {
    var isSelectedResolution by remember {
        mutableStateOf(false)
    }
    var downloadSpeed by remember {
        mutableStateOf("0 B/s")
    }
    var startTime by remember {
        mutableLongStateOf(0L)
    }
    var elapsedTimeSec by remember {
        mutableStateOf(0f)
    }
    LaunchedEffect(state.downloadProgress.totalDownloadedBytes) {
        elapsedTimeSec = (Clock.System.now().toEpochMilliseconds() - startTime) / 1000f
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
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
        ) {
            Text(
                text = "Get Video"
            )
        }
        Text(
            text = state.videoUrlM3U8?.title ?: "",
        )
        if (state.videoQualities.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            isSelectedResolution = true
                        },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = state.selectedVideoQuality?.resolution ?: "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(
                        modifier = Modifier
                            .width(4.dp),
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier
                            .rotate(if (isSelectedResolution) 180f else 0f),
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )
                DropdownMenu(
                    expanded = isSelectedResolution,
                    onDismissRequest = { isSelectedResolution = false },
                ) {
                    state.videoQualities.forEach { video ->
                        DropdownMenuItem(
                            onClick = {
                                isSelectedResolution = false
                                onSelectedVideoQuality(video)
                            },
                            text = {
                                Text(text = "${video.resolution} ${video.codecs}")
                            }
                        )
                    }
                }
            }
            Button(
                onClick = {
                    onDownloadVideo()
                    startTime = Clock.System.now().toEpochMilliseconds()
                },
            ) {
                Text(
                    text = "Download"
                )
            }
            CircularProgressIndicator(
                progress = { state.downloadProgress.progress },
            )
            Text(
                text = formatSpeed(state.downloadProgress.totalDownloadedBytes / elapsedTimeSec)
            )
        }
    }
}