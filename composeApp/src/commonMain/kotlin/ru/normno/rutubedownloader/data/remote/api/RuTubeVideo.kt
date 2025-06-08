package ru.normno.rutubedownloader.data.remote.api

import ru.normno.rutubedownloader.data.remote.KtorApiClient
import ru.normno.rutubedownloader.domain.model.Video
import ru.normno.rutubedownloader.util.dowload.Progress
import ru.normno.rutubedownloader.util.errorhendling.Error
import ru.normno.rutubedownloader.util.errorhendling.Result

class RuTubeVideo(
    private val ktorApiClient: KtorApiClient,
) {
    suspend fun getVideoById(id: String): Result<Video, Error> {
        return ktorApiClient.get<Video>(
            route = "/api/play/options/${id}",
            parameters = mapOf("format" to "json"),
        )
    }

    suspend fun downloadVideoPlaylist(url: String): Result<ByteArray, Error> {
        return ktorApiClient.downloadFile(
            fileUrl = url,
        )
    }

    suspend fun downloadHlsStream(
        url: String,
        name: String,
        isSharedStorage: Boolean = false,
        onProgress: (Progress.DownloadProgress) -> Unit = {}
    ): Result<Long, Error> {
        return ktorApiClient.downloadHlsStreamToFile(
            playlistUrl = url,
            outputFileName = name,
            isSharedStorage = isSharedStorage,
            onProgress = onProgress,
        )
    }
}