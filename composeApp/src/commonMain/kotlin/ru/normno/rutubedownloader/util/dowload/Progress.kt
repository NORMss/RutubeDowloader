package ru.normno.rutubedownloader.util.dowload

object Progress {
    data class DownloadProgress(
        val progress: Float = 0f,
        val totalDownloadedBytes: Long = 0L,
        val currentSpeed: Float = 0f,
    )

    fun formatSpeed(bytesPerSec: Float): String {
        return when {
            bytesPerSec > 1024 * 1024 -> "%.1f MB/s".format(bytesPerSec / (1024 * 1024))
            bytesPerSec > 1024 -> "%.1f KB/s".format(bytesPerSec / 1024)
            else -> "%.0f B/s".format(bytesPerSec)
        }
    }
}