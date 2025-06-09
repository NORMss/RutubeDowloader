package ru.normno.rutubedownloader.presentation.common

import androidx.compose.runtime.Composable

actual object PermissionManager {
    @Composable
    actual fun requestWritePermission(): Boolean {
        return true
    }
}