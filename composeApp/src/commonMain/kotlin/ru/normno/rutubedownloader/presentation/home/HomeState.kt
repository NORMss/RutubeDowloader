package ru.normno.rutubedownloader.presentation.home

import ru.normno.rutubedownloader.domain.model.Video
import ru.normno.rutubedownloader.util.video.ParseM3U8Playlist
import ru.normno.rutubedownloader.util.video.ParseM3U8Playlist.VideoQuality

data class HomeState(
    val videoUrlWithId: String = "",
    val videoUrlM3U8: Video? = null,
    val videoQuality: VideoQuality? = null,
)
