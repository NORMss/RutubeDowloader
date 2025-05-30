package ru.normno.rutubedownloader

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import ru.normno.rutubedownloader.presentation.home.HomeScreen
import ru.normno.rutubedownloader.presentation.home.HomeViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val viewModel = viewModel<HomeViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        HomeScreen(
            state = state,
            setVideoUrl = viewModel::setVideUrl,
        )
    }
}