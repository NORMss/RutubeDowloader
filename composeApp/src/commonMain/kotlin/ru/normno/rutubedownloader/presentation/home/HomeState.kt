package ru.normno.rutubedownloader.presentation.home

import io.github.vinceglb.filekit.PlatformFile
import ru.normno.rutubedownloader.domain.model.Video
import ru.normno.rutubedownloader.util.dowload.Progress
import ru.normno.rutubedownloader.util.video.ParseM3U8Playlist.VideoQuality

data class HomeState(
    val videoUrlWithId: String = "",
    val videoUrlM3U8: Video? = null,
    val videoQualities: List<VideoQuality> = emptyList(),
    val downloadProgress: Progress.DownloadProgress = Progress.DownloadProgress(),
    val selectedVideoQuality: VideoQuality? = null,
    val downloadedVideos: List<PlatformFile> = emptyList(),
    val isDownload: Boolean = false,
)
