package ru.normno.rutubedownloader.presentation.common

import androidx.compose.runtime.Composable

expect object PermissionManager {
    @Composable
    fun requestWritePermission(): Boolean
}