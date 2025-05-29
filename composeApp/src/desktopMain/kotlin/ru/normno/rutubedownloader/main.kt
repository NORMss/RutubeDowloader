package ru.normno.rutubedownloader

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ru.normno.rutubedownloader.di.AppModule.initializeKoin

fun main() = application {
    initializeKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "rutubeDownloader",
    ) {
        App()
    }
}