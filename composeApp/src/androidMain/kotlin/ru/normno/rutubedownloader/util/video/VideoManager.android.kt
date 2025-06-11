package ru.normno.rutubedownloader.util.video

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File

actual class VideoManager(
    private val context: Context,
) {
    actual fun openVideo(path: String) {
        try {
            val file = File(path)

            val uri: Uri = FileProvider.getUriForFile(
                context,
                "ru.normno.rutubedownloader.fileprovider",
                file
            )

            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "video/*")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Ошибка при открытии видео", Toast.LENGTH_SHORT).show()
        }
    }
}