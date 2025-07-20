package ru.normno.rutubedownloader.data.remote

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.normno.rutubedownloader.domain.remote.DownloadManager
import ru.normno.rutubedownloader.domain.repository.DownloaderRepository
import ru.normno.rutubedownloader.util.dowload.Progress

class WindowsDownloadManager(
    private val downloaderRepository: DownloaderRepository
) : DownloadManager {
    override fun startDownload(
        url: String,
        name: String,
        onProgress: (Progress.DownloadProgress) -> Unit
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            downloaderRepository.downloadHlsStream(
                url = url,
                name = name,
                onProgress = onProgress
            )
        }
    }
}