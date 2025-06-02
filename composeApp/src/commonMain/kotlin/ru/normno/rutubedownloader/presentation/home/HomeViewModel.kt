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

class HomeViewModel(
    private val downloaderRepository: DownloaderRepository,
) : ViewModel() {
    val state: StateFlow<HomeState>
        field = MutableStateFlow(HomeState())

    fun setVideUrl(string: String) {
        state.update {
            it.copy(
                videoUrlWithId = string,
            )
        }
    }

    fun getVideoById() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = downloaderRepository.getVideoById(state.value.videoUrlWithId)
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
    }
}