package ru.normno.rutubedownloader.util.file

import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.shareFile

actual suspend fun shareFile(fileKit: FileKit, file: PlatformFile) {
    fileKit.shareFile(file)
}