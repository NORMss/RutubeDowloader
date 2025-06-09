package ru.normno.rutubedownloader.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
actual object PermissionManager {
    @Composable
    actual fun requestWritePermission(): Boolean {
        val permissionState = rememberPermissionState(
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        LaunchedEffect(permissionState.status) {
            if (!permissionState.status.isGranted && !permissionState.status.shouldShowRationale) {
                permissionState.launchPermissionRequest()
            }
        }

        return permissionState.status.isGranted
    }
}
