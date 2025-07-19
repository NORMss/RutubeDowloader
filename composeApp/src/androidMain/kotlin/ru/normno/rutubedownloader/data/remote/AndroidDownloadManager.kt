package ru.normno.rutubedownloader.data.remote

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import ru.normno.rutubedownloader.domain.remote.DownloadManager
import ru.normno.rutubedownloader.service.DownloadService
import ru.normno.rutubedownloader.util.dowload.Progress

class AndroidDownloadManager(
    private val context: Context,
) : DownloadManager {
    private var connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
            val service = (binder as DownloadService.LocalBinder).getService()
            TODO("DOWNLOAD_PROGRESS_IMPL")
        }

        override fun onServiceDisconnected(name: ComponentName) {}
    }

    override fun startDownload(
        url: String,
        name: String,
        onProgress: (Progress.DownloadProgress) -> Unit
    ) {
        Intent(context, DownloadService::class.java).also {
            it.action = DownloadService.Actions.START.toString()
            context.startService(it)
        }
        val connection = getConnectionDownloadService()
        context.bindService(
            Intent(context, DownloadService::class.java),
            connection,
            BIND_AUTO_CREATE
        )
    }

    private fun getConnectionDownloadService(): ServiceConnection {
        return object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, binder: IBinder) {
                val service = (binder as DownloadService.LocalBinder).getService()
                TODO("DOWNLOAD_PROGRESS_IMPL")
            }

            override fun onServiceDisconnected(name: ComponentName) {}
        }
    }
}