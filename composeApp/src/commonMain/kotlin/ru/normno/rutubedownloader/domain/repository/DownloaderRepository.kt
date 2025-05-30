package ru.normno.rutubedownloader.domain.repository

import ru.normno.rutubedownloader.domain.model.Video

interface DownloaderRepository {
    fun getVideoById(id: String): Video
}