@file:OptIn(ExperimentalMaterial3Api::class)

package ru.normno.rutubedownloader.presentation.home.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun LinearProgressIndicatorSegments(
    segments: List<ProgressSegment>,
    modifier: Modifier = Modifier,
    trackColor: Color =  ProgressIndicatorDefaults.linearTrackColor,
    strokeCap: StrokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
) {
    Canvas(
        modifier
            .size(LinearIndicatorWidth, LinearIndicatorHeight)
    ) {
        val width = size.width
        val height = size.height
        val yOffset = height / 2

        drawLine(
            color = trackColor,
            start = Offset(0f, yOffset),
            end = Offset(width, yOffset),
            strokeWidth = height,
            cap = strokeCap
        )

        var startFraction = 0f

        segments.forEach { segment ->
            val segmentProgress = segment.progress.coerceIn(0f, 1f)
            val endFraction = (startFraction + segmentProgress).coerceIn(0f, 1f)

            if (endFraction > startFraction) {
                drawLine(
                    color = segment.color,
                    start = Offset(startFraction * width, yOffset),
                    end = Offset(endFraction * width, yOffset),
                    strokeWidth = height,
                    cap = strokeCap
                )
            }

            startFraction = endFraction
        }
    }
}

data class ProgressSegment(
    val progress: Float = 0f,
    val color: Color,
)

internal val LinearIndicatorWidth = 240.dp

internal val LinearIndicatorHeight = 4.dp