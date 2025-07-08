package ru.normno.rutubedownloader.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.vinceglb.filekit.PlatformFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.normno.rutubedownloader.domain.repository.DownloaderRepository
import ru.normno.rutubedownloader.domain.repository.FileRepository
import ru.normno.rutubedownloader.util.errorhendling.Error
import ru.normno.rutubedownloader.util.errorhendling.Result
import ru.normno.rutubedownloader.util.platform.ShareLinkProvider
import ru.normno.rutubedownloader.util.validate.SanitizeFileName.sanitizeFileName
import ru.normno.rutubedownloader.util.video.ParseM3U8Playlist.VideoQuality
import ru.normno.rutubedownloader.util.video.ParseM3U8Playlist.parseM3U8Playlist
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class HomeViewModel(
    private val downloaderRepository: DownloaderRepository,
    private val fileRepository: FileRepository,
    private val shareLinkProvider: ShareLinkProvider,
) : ViewModel() {
    val state: StateFlow<HomeState>
        field = MutableStateFlow(HomeState())

    private val _errorEvent = Channel<Error>()
    val errorEvent = _errorEvent.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllVideos()
            getDiskInfo()
        }
        shareLinkProvider.getSharedLink()?.also {
            setVideoUrl(it)
            getVideoById()
        }
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnDeleteVideo -> onDeleteFile(action.platformFile)
            HomeAction.OnDownloadVideo -> onDownloadVideo()
            HomeAction.OnGetVideo -> getVideoById()
            is HomeAction.OnSelectedVideoQuality -> onSelectedVideoQuality(action.videoQuality)
            is HomeAction.OnShareVideo -> onShareVideo(action.platformFile)
            is HomeAction.SetVideoUrl -> setVideoUrl(action.str)
        }
    }

    private fun onDeleteFile(file: PlatformFile) {
        viewModelScope.launch(Dispatchers.IO) {
            fileRepository.deleteFile(file)
            getAllVideos()
        }
    }

    private fun onShareVideo(file: PlatformFile) {
        viewModelScope.launch(Dispatchers.IO) {
            fileRepository.shareVideo(file)
        }
    }

    private fun setVideoUrl(string: String) {
        state.update {
            it.copy(
                videoUrlWithId = string,
            )
        }
    }

    private fun onSelectedVideoQuality(videoQuality: VideoQuality) {
        state.update {
            it.copy(
                selectedVideoQuality = videoQuality,
            )
        }
    }

    private fun onDownloadVideo() {
        state.update {
            it.copy(
                isDownload = true,
            )
        }
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
                        state.update {
                            it.copy(
                                isDownload = false,
                            )
                        }
                    }

                    is Result.Succes -> {
                        getAllVideos()
                        state.update {
                            it.copy(
                                isDownload = false,
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getVideoById() {
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
                            sendError(result.error)
                        }

                        is Result.Succes -> {
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

                        is Result.Succes -> {
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

    private suspend fun getDiskInfo() {
        state.update {
            it.copy(
                disk = fileRepository.getUsedSpace()
            )
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

    private fun sendError(error: Error?) {
        error?.let {
            viewModelScope.launch {
                _errorEvent.send(error)
            }
        }
    }
}