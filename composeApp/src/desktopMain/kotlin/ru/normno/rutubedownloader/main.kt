package ru.normno.rutubedownloader

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.vinceglb.filekit.FileKit
import ru.normno.rutubedownloader.di.AppModule.initializeKoin

fun main() = application {
    FileKit.init("ru.normno.rutubedownloader")
    initializeKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "rutubeDownloader",
    ) {
        App()
    }
}