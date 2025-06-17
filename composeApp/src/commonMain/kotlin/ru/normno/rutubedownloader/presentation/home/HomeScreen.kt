@file:OptIn(ExperimentalTime::class)

package ru.normno.rutubedownloader.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.name
import io.github.vinceglb.filekit.path
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import ru.normno.rutubedownloader.domain.Language
import ru.normno.rutubedownloader.presentation.component.VideoCard
import ru.normno.rutubedownloader.util.dowload.Progress.formatSpeed
import ru.normno.rutubedownloader.util.video.ParseM3U8Playlist.VideoQuality
import rutubedownloader.composeapp.generated.resources.Res
import rutubedownloader.composeapp.generated.resources.cancel
import rutubedownloader.composeapp.generated.resources.delete
import rutubedownloader.composeapp.generated.resources.delete_video
import rutubedownloader.composeapp.generated.resources.get_video
import rutubedownloader.composeapp.generated.resources.no_videos_downloaded
import rutubedownloader.composeapp.generated.resources.sure_to_delete
import rutubedownloader.composeapp.generated.resources.url_to_video
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
fun HomeScreen(
    state: HomeState,
    setVideoUrl: (String) -> Unit,
    onSelectedVideoQuality: (VideoQuality) -> Unit,
    onDownloadVideo: () -> Unit,
    onGetVideo: () -> Unit,
    onOpenVideo: (path: String) -> Unit,
    onShareVideo: (PlatformFile) -> Unit,
    onDeleteVideo: (PlatformFile) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isShowDeleteVideoDialog by remember {
        mutableStateOf(false)
    }
    var deleteVideoFile by remember {
        mutableStateOf<PlatformFile?>(null)
    }
    var isSelectedResolution by remember {
        mutableStateOf(false)
    }
    var startTime by remember {
        mutableLongStateOf(0L)
    }
    var elapsedTimeSec by remember {
        mutableFloatStateOf(0f)
    }
    LaunchedEffect(state.isDownload) {
        elapsedTimeSec = if (state.isDownload) {
            (Clock.System.now().toEpochMilliseconds() - startTime) / 1000f
        } else {
            0f
        }
    }

    if (isShowDeleteVideoDialog) {
        AlertDialog(
            icon = {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                )
            },
            title = {
                Text(
                    text = stringResource(Res.string.delete_video),
                )
            },
            text = {
                Text(
                    text = stringResource(Res.string.sure_to_delete),
                )
            },
            onDismissRequest = {
                isShowDeleteVideoDialog = false
            },
            confirmButton = {
                Button(
                    onClick = {
                        deleteVideoFile?.let {
                            onDeleteVideo(it)
                        }
                        isShowDeleteVideoDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError,
                    )
                ) {
                    Text(
                        text = stringResource(Res.string.delete),
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        isShowDeleteVideoDialog = false
                    },
                ) {
                    Text(
                        text = stringResource(Res.string.cancel),
                    )
                }
            }
        )
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(
                    top = 32.dp,
                )
                .padding(
                    horizontal = 8.dp,
                ),
        ) {
            TextField(
                value = state.videoUrlWithId,
                onValueChange = setVideoUrl,
                singleLine = true,
                modifier = Modifier
                    .weight(1f),
                label = {
                    Text(
                        text = stringResource(Res.string.url_to_video)
                    )
                },
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onGetVideo()
                    }
                )
            )
            Spacer(
                modifier = Modifier
                    .width(4.dp)
            )
            Button(
                onClick = onGetVideo,
                enabled = state.videoUrlWithId.isNotBlank(),
            ) {
                Text(
                    text = stringResource(Res.string.get_video)
                )
            }
        }
        Spacer(
            modifier = Modifier
                .height(8.dp)
        )
        AsyncImage(
            model = state.videoUrlM3U8?.previewUrl,
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth(0.9f),
            onSuccess = { println("Success: ${state.videoUrlM3U8?.previewUrl}") },
            onError = { println("Error: ${it.result.throwable.message}") },
        )
        Spacer(
            modifier = Modifier
                .height(8.dp)
        )
        Text(
            text = state.videoUrlM3U8?.title ?: "",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(horizontal = 8.dp),
        )
        if (state.videoQualities.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 8.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
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
                            style = MaterialTheme.typography.titleMedium,
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
                if (state.isDownload) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        CircularProgressIndicator(
                            progress = { state.downloadProgress.progress },
                        )
                        Text(
                            text = if (elapsedTimeSec > 0) {
                                formatSpeed(state.downloadProgress.totalDownloadedBytes / elapsedTimeSec)
                            } else {
                                "Calculating..."
                            }
                        )
                    }
                } else {
                    Button(
                        onClick = {
                            onDownloadVideo()
                            startTime = Clock.System.now().toEpochMilliseconds()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Download,
                            contentDescription = null,
                        )
                    }
                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (state.downloadedVideos.isEmpty()) {
                item {
                    Text(
                        text = stringResource(Res.string.no_videos_downloaded),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    )
                }
            }
            items(
                count = state.downloadedVideos.size,
                key = {
                    state.downloadedVideos[it].name
                }
            ) {
                VideoCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    name = state.downloadedVideos[it].name,
                    onOpenVideo = {
                        onOpenVideo(state.downloadedVideos[it].path)
                    },
                    onShare = {
                        onShareVideo(state.downloadedVideos[it])
                    },
                    onDeleteVideo = {
                        deleteVideoFile = state.downloadedVideos[it]
                        isShowDeleteVideoDialog = true
                    },
                )
            }
        }
    }
}