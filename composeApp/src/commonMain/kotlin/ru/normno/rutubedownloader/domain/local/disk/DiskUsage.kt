package ru.normno.rutubedownloader.domain.local.disk

import ru.normno.rutubedownloader.domain.model.Disk

interface DiskUsage {
    suspend fun getUsedSpace(): Disk
}