package ru.normno.rutubedownloader.util.file

import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.PlatformFile

expect suspend fun shareFile(fileKit: FileKit, file: PlatformFile)