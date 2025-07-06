package ru.normno.rutubedownloader.data.locale.disk

import ru.normno.rutubedownloader.domain.local.disk.DiskUsage
import ru.normno.rutubedownloader.domain.model.Disk

import java.io.File

open class DiskUsageImplDesktop: DiskUsage {
    override suspend fun getUsedSpace(): Disk {
        val disk = File("C:\\")
        return Disk(
            total = disk.totalSpace,
            free = disk.freeSpace,
        )
    }
}