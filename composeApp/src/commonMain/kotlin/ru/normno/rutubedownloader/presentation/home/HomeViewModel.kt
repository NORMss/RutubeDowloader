package ru.normno.rutubedownloader.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.vinceglb.filekit.PlatformFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.normno.rutubedownloader.domain.repository.DownloaderRepository
import ru.normno.rutubedownloader.domain.repository.FileRepository
import ru.normno.rutubedownloader.util.errorhendling.Result
import ru.normno.rutubedownloader.util.validate.SanitizeFileName.sanitizeFileName
import ru.normno.rutubedownloader.util.video.ParseM3U8Playlist.VideoQuality
import ru.normno.rutubedownloader.util.video.ParseM3U8Playlist.parseM3U8Playlist
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class HomeViewModel(
    private val downloaderRepository: DownloaderRepository,
    private val fileRepository: FileRepository,
) : ViewModel() {
    val state: StateFlow<HomeState>
        field = MutableStateFlow(HomeState())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllVideos()
        }
    }

    fun onShareVideo(file: PlatformFile) {
        viewModelScope.launch(Dispatchers.IO) {
            fileRepository.shareVideo(file)
        }
    }

    fun setVideoUrl(string: String) {
        state.update {
            it.copy(
                videoUrlWithId = string,
            )
        }
    }

    fun onSelectedVideoQuality(videoQuality: VideoQuality) {
        state.update {
            it.copy(
                selectedVideoQuality = videoQuality,
            )
        }
    }

    fun onDownloadVideo() {
        state.value.selectedVideoQuality?.let { videoQuality ->
            viewModelScope.launch(Dispatchers.Default) {
                val result = downloaderRepository.downloadHlsStream(
                    url = videoQuality.url,
                    name = sanitizeFileName(
                        state.value.videoUrlM3U8?.title
                            ?: Clock.System.now().epochSeconds.toString()
                    ) + ".mp4",
                    onProgress = { downloadProgress ->
                        state.update {
                            it.copy(
                                downloadProgress = downloadProgress,
                            )
                        }
                    }
                )
                when (result) {
                    is Result.Error -> {

                    }

                    is Result.Success -> {
                        getAllVideos()
                    }
                }
            }
        }
    }

    fun getVideoById() {
        if (state.value.videoUrlWithId.isNotBlank()) {
            viewModelScope.launch(Dispatchers.IO) {
                state.value.videoUrlWithId.let { url ->
                    val result = downloaderRepository.getVideoById(
                        if (url.contains("video")) {
                            url.substringAfter("video/").substringBefore("/")
                        } else {
                            url
                        }
                    )
                    when (result) {
                        is Result.Error -> {

                        }

                        is Result.Success -> {
                            state.update {
                                it.copy(
                                    videoUrlM3U8 = result.data
                                )
                            }
                        }
                    }
                }
                state.value.videoUrlM3U8?.videoUrl?.m3u8Playlist?.let { url ->
                    val result = downloaderRepository.downloadVideoPlaylist(url)
                    when (result) {
                        is Result.Error -> {

                        }

                        is Result.Success -> {
                            result.data?.decodeToString()?.let { m3u8 ->
                                val videoQualities = parseM3U8Playlist(m3u8)
                                state.update {
                                    it.copy(
                                        videoQualities = videoQualities,
                                        selectedVideoQuality = videoQualities.first(),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun getAllVideos() {
        fileRepository.getAllDataVideos().also { files ->
            state.update {
                it.copy(
                    downloadedVideos = files,
                )
            }
        }
    }
}