package ru.normno.rutubedownloader.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class DownloadService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        when(intent?.action){
            Actions.START.toString() -> {}
            Actions.STOP.toString() -> {}
        }
        return super.onStartCommand(intent, flags, startId)
    }

    enum class Actions {
        START, STOP
    }
}