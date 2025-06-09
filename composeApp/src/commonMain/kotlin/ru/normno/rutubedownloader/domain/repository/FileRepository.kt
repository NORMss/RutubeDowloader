package ru.normno.rutubedownloader.domain.repository

import io.github.vinceglb.filekit.PlatformFile

interface FileRepository {
    suspend fun saveVideo(
        byteArray: ByteArray,
        name: String,
    )

    suspend fun getAllDataVideos(): List<PlatformFile>
}