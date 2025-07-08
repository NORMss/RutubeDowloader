package ru.normno.rutubedownloader.presentation.home

import io.github.vinceglb.filekit.PlatformFile
import ru.normno.rutubedownloader.util.video.ParseM3U8Playlist

sealed interface HomeAction {
    data class SetVideoUrl(val str: String) : HomeAction
    data class OnSelectedVideoQuality(val videoQuality: ParseM3U8Playlist.VideoQuality) : HomeAction
    data object OnDownloadVideo : HomeAction
    data object OnGetVideo : HomeAction
    data class OnShareVideo(val platformFile: PlatformFile) : HomeAction
    data class OnDeleteVideo(val platformFile: PlatformFile) : HomeAction
}