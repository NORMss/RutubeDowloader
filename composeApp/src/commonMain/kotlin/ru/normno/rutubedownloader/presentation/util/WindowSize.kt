package ru.normno.rutubedownloader.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalWindowInfo

data class WindowSize(
    val with: WindowType,
    val height: WindowType,
)

enum class WindowType {
    Compact, Medium, Expanded
}

@Composable
fun rememberWindowSize(): WindowSize {
    val configuration = LocalWindowInfo.current

    return WindowSize(
        with = when {
            configuration.containerSize.width < 600 -> WindowType.Compact
            configuration.containerSize.width < 840 -> WindowType.Medium
            else -> WindowType.Expanded
        },
        height = when {
            configuration.containerSize.height < 600 -> WindowType.Compact
            configuration.containerSize.height < 840 -> WindowType.Medium
            else -> WindowType.Expanded
        }
    )
}