package ru.normno.rutubedownloader.data.repository

import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.openFileSaver
import io.github.vinceglb.filekit.filesDir
import io.github.vinceglb.filekit.list
import io.github.vinceglb.filekit.write
import ru.normno.rutubedownloader.domain.repository.FileRepository

class FileRepositoryImpl(
    private val fileKit: FileKit,
) : FileRepository {
    override suspend fun saveVideo(
        byteArray: ByteArray,
        name: String,
    ) {
        fileKit.openFileSaver(
            suggestedName = name,
            extension = "mp4",
        ).also { file ->
            file?.write(byteArray)
        }
    }

    override suspend fun getAllDataVideos(): List<PlatformFile> {
        return FileKit.filesDir.list()
    }
}