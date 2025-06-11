package ru.normno.rutubedownloader

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.vinceglb.filekit.FileKit
import org.koin.core.context.startKoin
import ru.normno.rutubedownloader.di.AppModule.createKoinConfiguration

fun main() = application {
    FileKit.init("ru.normno.rutubedownloader")
    Window(
        onCloseRequest = ::exitApplication,
        title = "rutubeDownloader",
    ) {
        App()
    }
}