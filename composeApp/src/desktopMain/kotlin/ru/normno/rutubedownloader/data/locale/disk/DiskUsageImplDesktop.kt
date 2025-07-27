package ru.normno.rutubedownloader.data.locale.disk

import io.github.vinceglb.filekit.FileKit
import ru.normno.rutubedownloader.domain.local.disk.DiskUsage
import ru.normno.rutubedownloader.domain.model.Disk

import java.io.File

open class DiskUsageImplDesktop: DiskUsage {
    override suspend fun getUsedSpace(): Disk {
        val disk = File(System.getProperty("user.dir"))
        return Disk(
            total = disk.totalSpace,
            free = disk.freeSpace,
        )
    }
}