package ru.normno.rutubedownloader.domain.repository

import ru.normno.rutubedownloader.domain.model.Video
import ru.normno.rutubedownloader.util.dowload.Progress
import ru.normno.rutubedownloader.util.errorhendling.Error
import ru.normno.rutubedownloader.util.errorhendling.Result

interface DownloaderRepository {
    suspend fun getVideoById(id: String): Result<Video, Error>
    suspend fun downloadVideoPlaylist(url: String): Result<ByteArray, Error>
    suspend fun downloadHlsStream(
        url: String,
        name: String,
        isSharedStorage: Boolean = false,
        onProgress: (Progress.DownloadProgress) -> Unit = {}
    ): Result<Long, Error>
}