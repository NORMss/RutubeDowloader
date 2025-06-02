package ru.normno.rutubedownloader.presentation.home

import ru.normno.rutubedownloader.domain.model.Video

data class HomeState(
    val videoUrlWithId: String = "",
    val videoUrlM3U8: Video? = null,
)
