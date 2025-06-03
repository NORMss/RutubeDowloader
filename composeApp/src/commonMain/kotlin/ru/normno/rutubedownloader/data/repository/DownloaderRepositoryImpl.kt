package ru.normno.rutubedownloader.data.repository

import ru.normno.rutubedownloader.data.remote.api.RuTubeVideo
import ru.normno.rutubedownloader.domain.model.Video
import ru.normno.rutubedownloader.domain.repository.DownloaderRepository
import ru.normno.rutubedownloader.util.errorhendling.Error
import ru.normno.rutubedownloader.util.errorhendling.Result

class DownloaderRepositoryImpl(
    private val ruTubeVideo: RuTubeVideo,
) : DownloaderRepository {
    override suspend fun getVideoById(id: String): Result<Video, Error> {
        return ruTubeVideo.getVideoById(id = id)
    }

    override suspend fun downloadVideoPlaylist(url: String): Result<ByteArray, Error> {
        return ruTubeVideo.downloadVideoPlaylist(url = url)
    }

    override suspend fun downloadHlsStream(url: String, onProgress: (Float) -> Unit): Result<ByteArray, Error> {
        return ruTubeVideo.downloadHlsStream(
            url = url,
            onProgress = onProgress,
        )
    }
}