package ru.normno.rutubedownloader.data.repository

import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.delete
import io.github.vinceglb.filekit.dialogs.openFileSaver
import io.github.vinceglb.filekit.filesDir
import io.github.vinceglb.filekit.list
import io.github.vinceglb.filekit.write
import ru.normno.rutubedownloader.data.locale.disk.PlatformDiskUsage
import ru.normno.rutubedownloader.domain.model.Disk
import ru.normno.rutubedownloader.domain.repository.FileRepository
import ru.normno.rutubedownloader.util.file.shareFile

class FileRepositoryImpl(
    private val fileKit: FileKit,
    private val platformDiskUsage: PlatformDiskUsage
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

    override suspend fun shareVideo(file: PlatformFile) {
        shareFile(fileKit, file)
    }

    override suspend fun deleteFile(file: PlatformFile) {
        file.delete()
    }

    override suspend fun getUsedSpace(): Disk {
        return platformDiskUsage.getUsedSpace()
    }
}