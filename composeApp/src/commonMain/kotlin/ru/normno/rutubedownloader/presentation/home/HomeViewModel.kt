package ru.normno.rutubedownloader.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.normno.rutubedownloader.domain.repository.DownloaderRepository
import ru.normno.rutubedownloader.util.errorhendling.Result
import ru.normno.rutubedownloader.util.video.ParseM3U8Playlist.parseM3U8Playlist

class HomeViewModel(
    private val downloaderRepository: DownloaderRepository,
) : ViewModel() {
    val state: StateFlow<HomeState>
        field = MutableStateFlow(HomeState())

    fun setVideoUrl(string: String) {
        state.update {
            it.copy(
                videoUrlWithId = string,
            )
        }
    }

    fun getVideoById() {
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
                        result.data?.decodeToString()?.let {
                            println(parseM3U8Playlist(it))
                        }
                    }
                }
            }
        }
    }
}