package ru.normno.rutubedownloader.data.locale.disk

import android.os.Environment
import android.os.StatFs
import ru.normno.rutubedownloader.domain.local.disk.DiskUsage
import ru.normno.rutubedownloader.domain.model.Disk

open class DiskUsageImplAndroid: DiskUsage {
    override suspend fun getUsedSpace(): Disk {
        val path = Environment.getDataDirectory()
        val stat = StatFs(path.path)

        val totalBytes = stat.totalBytes
        val availableBytes = stat.availableBytes

        return Disk(
            total = totalBytes,
            free = availableBytes
        )
    }
}