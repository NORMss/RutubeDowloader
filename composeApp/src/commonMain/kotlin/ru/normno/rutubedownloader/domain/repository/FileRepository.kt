package ru.normno.rutubedownloader.domain.repository

interface FileRepository {
    suspend fun saveVideo()
}