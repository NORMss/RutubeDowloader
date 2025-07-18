package ru.normno.rutubedownloader.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.core.app.NotificationCompat

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

    private fun createNotification(contentText: String): Notification {
        val channelId = "download_channel"
        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(channelId, "Загрузки", NotificationManager.IMPORTANCE_LOW)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("KMP Загрузка")
            .setContentText(contentText)
            .build()
    }

    enum class Actions {
        START, STOP
    }
}