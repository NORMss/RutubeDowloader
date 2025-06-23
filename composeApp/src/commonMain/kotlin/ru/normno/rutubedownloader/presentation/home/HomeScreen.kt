@file:OptIn(ExperimentalTime::class, ExperimentalMaterial3WindowSizeClassApi::class)

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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import org.jetbrains.compose.resources.stringResource
import ru.normno.rutubedownloader.domain.model.Video
import ru.normno.rutubedownloader.presentation.common.calculateWindowSizeClass
import ru.normno.rutubedownloader.presentation.home.component.VideoCard
import ru.normno.rutubedownloader.util.dowload.Progress
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
    val size = calculateWindowSizeClass()
    when (size.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            Column(
                modifier = modifier,
            ) {
                VideoCard(
                    videoUrlWithId = state.videoUrlWithId,
                    setVideoUrl = setVideoUrl,
                    videoUrlM3U8 = state.videoUrlM3U8,
                    videoQualities = state.videoQualities,
                    selectedVideoQuality = state.selectedVideoQuality,
                    isDownload = state.isDownload,
                    downloadProgress = state.downloadProgress,
                    onSelectedVideoQuality = onSelectedVideoQuality,
                    onDownloadVideo = onDownloadVideo,
                    onGetVideo = onGetVideo,
                    onOpenVideo = onOpenVideo,
                    onShare = onShareVideo,
                    onDeleteVideo = onDeleteVideo,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 8.dp,
                            horizontal = 8.dp,
                        ),
                )
                VideosList(
                    downloadedVideos = state.downloadedVideos,
                    onOpenVideo = onOpenVideo,
                    onShareVideo = onShareVideo,
                    onDeleteVideo = onDeleteVideo,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 8.dp,
                            horizontal = 8.dp,
                        ),
                )
            }
        }

        else -> {
            Row(
                modifier = modifier,
            ) {
                VideoCard(
                    videoUrlWithId = state.videoUrlWithId,
                    setVideoUrl = setVideoUrl,
                    videoUrlM3U8 = state.videoUrlM3U8,
                    videoQualities = state.videoQualities,
                    selectedVideoQuality = state.selectedVideoQuality,
                    isDownload = state.isDownload,
                    downloadProgress = state.downloadProgress,
                    onSelectedVideoQuality = onSelectedVideoQuality,
                    onDownloadVideo = onDownloadVideo,
                    onGetVideo = onGetVideo,
                    onOpenVideo = onOpenVideo,
                    onShare = onShareVideo,
                    onDeleteVideo = onDeleteVideo,
                    modifier = Modifier
                        .weight(1f)
                        .padding(
                            vertical = 8.dp,
                            horizontal = 8.dp,
                        ),
                )
                Spacer(
                    modifier = Modifier
                        .width(8.dp),
                )
                VideosList(
                    downloadedVideos = state.downloadedVideos,
                    onOpenVideo = onOpenVideo,
                    onShareVideo = onShareVideo,
                    onDeleteVideo = onDeleteVideo,
                    modifier = Modifier
                        .weight(1f)
                        .padding(
                            vertical = 8.dp,
                            horizontal = 8.dp,
                        ),
                )
            }
        }

    }
}

@Composable
fun VideoCard(
    onOpenVideo: (path: String) -> Unit,
    onShare: (PlatformFile) -> Unit,
    onDeleteVideo: (PlatformFile) -> Unit,
    videoUrlWithId: String = "",
    setVideoUrl: (String) -> Unit = {},
    onGetVideo: () -> Unit = {},
    videoUrlM3U8: Video? = null,
    onSelectedVideoQuality: (VideoQuality) -> Unit = {},
    onDownloadVideo: () -> Unit = {},
    videoQualities: List<VideoQuality> = emptyList(),
    selectedVideoQuality: VideoQuality? = null,
    isDownload: Boolean = false,
    downloadProgress: Progress.DownloadProgress,
    modifier: Modifier = Modifier,
) {
    var isSelectedResolution by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextField(
                value = videoUrlWithId,
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
            OutlinedButton(
                onClick = onGetVideo,
                enabled = videoUrlWithId.isNotBlank(),
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
            model = videoUrlM3U8?.previewUrl,
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth(0.9f),
            onSuccess = { println("Success: ${videoUrlM3U8?.previewUrl}") },
            onError = { println("Error: ${it.result.throwable.message}") },
        )
        Spacer(
            modifier = Modifier
                .height(8.dp)
        )
        Text(
            text = videoUrlM3U8?.title ?: "",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(horizontal = 8.dp),
        )
        if (videoQualities.isNotEmpty()) {
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
                            text = selectedVideoQuality?.resolution ?: "",
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
                        videoQualities.forEach { video ->
                            DropdownMenuItem(
                                onClick = {
                                    isSelectedResolution = false
                                    onSelectedVideoQuality(video)
                                },
                                text = {
                                    Text(text = video.resolution)
                                }
                            )
                        }
                    }
                }
                if (isDownload) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        CircularProgressIndicator(
                            progress = { downloadProgress.progress },
                        )
                        Text(
                            text = formatSpeed(downloadProgress.currentSpeed)
                        )
                    }
                } else {
                    Button(
                        onClick = {
                            onDownloadVideo()
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
    }
}

@Composable
fun VideosList(
    downloadedVideos: List<PlatformFile>,
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


    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (downloadedVideos.isEmpty()) {
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
            count = downloadedVideos.size,
            key = {
                downloadedVideos[it].name
            }
        ) {
            VideoCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                name = downloadedVideos[it].name,
                onOpenVideo = {
                    onOpenVideo(downloadedVideos[it].path)
                },
                onShare = {
                    onShareVideo(downloadedVideos[it])
                },
                onDeleteVideo = {
                    deleteVideoFile = downloadedVideos[it]
                    isShowDeleteVideoDialog = true
                },
            )
        }
    }
}