package ru.normno.rutubedownloader.data.repository

import ru.normno.rutubedownloader.domain.model.Video
import ru.normno.rutubedownloader.domain.repository.DownloaderRepository

class DownloaderRepositoryImpl: DownloaderRepository {
    override fun getVideoById(id: String): Video {
        TODO("Not yet implemented")
    }
}