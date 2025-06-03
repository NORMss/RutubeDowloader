package ru.normno.rutubedownloader

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import ru.normno.rutubedownloader.presentation.home.HomeScreen
import ru.normno.rutubedownloader.presentation.home.HomeViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val viewModel = koinViewModel<HomeViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        HomeScreen(
            state = state,
            setVideoUrl = viewModel::setVideoUrl,
            onSelectedVideoQuality = viewModel::onSelectedVideoQuality,
            onDownloadVideo = viewModel::onDownloadVideo,
            onGetVideo = viewModel::getVideoById
        )
    }
}