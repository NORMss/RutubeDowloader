package ru.normno.rutubedownloader.presentation.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {
    val state: StateFlow<HomeState>
        field = MutableStateFlow(HomeState())

    fun setVideUrl(string: String) {
        state.update {
            it.copy(
                videoUrl = string,
            )
        }
    }
}