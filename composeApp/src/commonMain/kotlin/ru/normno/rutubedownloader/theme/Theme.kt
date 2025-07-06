package ru.normno.rutubedownloader.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

val LightColorTheme = lightColorScheme(
    primary = Primary,
    surface = Surface,
    surfaceVariant = SurfaceVariant,
    surfaceContainerLowest = SurfaceLowest,
    background = Background,
    onSurface = OnSurface,
    onBackground = OnBackground,
    surfaceContainerHighest = SurfaceContainerHighest,
    error = Error,
    onError = OnError,
)

val DarkColorTheme = darkColorScheme(
    primary = PrimaryDark,
    surface = SurfaceDark,
    surfaceVariant = SurfaceVariant,
    surfaceContainerLowest = SurfaceLowestDark,
    background = BackgroundDark,
    onSurface = OnSurfaceDark,
    onBackground = OnBackgroundDark,
    surfaceContainerHighest = SurfaceContainerHighestDark,
    error = Error,
    onError = OnError,
)

@Composable
fun RuTubeDownloader(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        content = content,
        colorScheme = if (isSystemInDarkTheme()) DarkColorTheme else LightColorTheme,
    )
}