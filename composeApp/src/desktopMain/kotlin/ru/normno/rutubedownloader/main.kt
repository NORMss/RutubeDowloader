package ru.normno.rutubedownloader

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.vinceglb.filekit.FileKit
import org.jetbrains.compose.resources.painterResource
import rutubedownloader.composeapp.generated.resources.Res
import rutubedownloader.composeapp.generated.resources.rutubeDownloader_icon
import java.awt.Dimension

fun main() = application {
    FileKit.init("ru.normno.rutubedownloader")
    Window(
        onCloseRequest = ::exitApplication,
        title = "RuTube Downloader",
        icon = painterResource(Res.drawable.rutubeDownloader_icon),
    ) {
        window.minimumSize = Dimension(360, 360)
        App()
    }
}