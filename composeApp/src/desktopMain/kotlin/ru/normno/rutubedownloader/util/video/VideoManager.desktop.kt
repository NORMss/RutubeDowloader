package ru.normno.rutubedownloader.util.video

import java.awt.Desktop
import java.io.File

actual class VideoManager {
    actual fun openVideo(path: String) {
        try {
            val file = File(path)
            if (!file.exists()) {
                println("File not found: $path")
                return
            }

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file)
            } else {
                println("Desktop is not supported on this platform.")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println("Error opening video: ${e.message}")
        }
    }
}
