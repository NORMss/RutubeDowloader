@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package ru.normno.rutubedownloader.presentation.common

import androidx.activity.compose.LocalActivity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable

@Composable
actual fun calculateWindowSizeClass() = calculateWindowSizeClass(
    LocalActivity.current!!
)