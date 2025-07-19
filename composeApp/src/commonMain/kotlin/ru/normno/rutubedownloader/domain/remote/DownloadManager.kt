package ru.normno.rutubedownloader.domain.remote

import ru.normno.rutubedownloader.util.dowload.Progress

interface DownloadManager {
    fun startDownload(
        url: String,
        name: String,
        onProgress: (Progress.DownloadProgress) -> Unit = {}
    )
}