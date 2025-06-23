package ru.normno.rutubedownloader.util.video

import ru.normno.rutubedownloader.R
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import ru.normno.rutubedownloader.BuildConfig
import java.io.File

actual class VideoManager(
    private val context: Context,
) {
    actual fun openVideo(path: String) {
        try {
            val file = File(path)

            val uri: Uri = FileProvider.getUriForFile(
                context,
                "${BuildConfig.APPLICATION_ID}.fileprovider",
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
            Toast.makeText(context, context.getString(R.string.error_open_video), Toast.LENGTH_SHORT).show()
        }
    }
}