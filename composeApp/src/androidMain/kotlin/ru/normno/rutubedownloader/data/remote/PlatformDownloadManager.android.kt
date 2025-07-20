package ru.normno.rutubedownloader.data.remote

import ru.normno.rutubedownloader.domain.remote.DownloadManager

actual fun getDownloadManager(): DownloadManager {
    return AndroidDownloadManager(
        context = TODO()
    )
}