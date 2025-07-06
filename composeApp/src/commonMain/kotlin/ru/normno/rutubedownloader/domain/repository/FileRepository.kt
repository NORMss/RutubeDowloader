package ru.normno.rutubedownloader.domain.repository

import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.delete
import ru.normno.rutubedownloader.domain.model.Disk

interface FileRepository {
    suspend fun saveVideo(
        byteArray: ByteArray,
        name: String,
    )

    suspend fun getAllDataVideos(): List<PlatformFile>

    suspend fun shareVideo(file: PlatformFile)

    suspend fun deleteFile(file: PlatformFile)

    suspend fun getUsedSpace(): Disk
}