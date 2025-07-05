package ru.normno.rutubedownloader.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

val LightColorTheme = lightColorScheme(
    primary = Primary,
    surface = Surface,
    surfaceContainerLowest = SurfaceLowest,
    background = Background,
    onSurface = OnSurface,
    onBackground = OnBackground,
    surfaceContainerHighest = SurfaceContainerHighest,
    error = Error,
)

val DarkColorTheme = darkColorScheme(
    primary = PrimaryDark,
    surface = SurfaceDark,
    surfaceContainerLowest = SurfaceLowestDark,
    background = BackgroundDark,
    onSurface = OnSurfaceDark,
    onBackground = OnBackgroundDark,
    surfaceContainerHighest = SurfaceContainerHighestDark,
    error = Error,
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