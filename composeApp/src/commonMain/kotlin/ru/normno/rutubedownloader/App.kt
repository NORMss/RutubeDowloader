@file:OptIn(KoinExperimentalAPI::class)

package ru.normno.rutubedownloader

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.burnoo.compose.remembersetting.rememberStringSetting
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinMultiplatformApplication
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import ru.normno.rutubedownloader.di.AppModule.createKoinConfiguration
import ru.normno.rutubedownloader.domain.Language
import ru.normno.rutubedownloader.domain.Localization
import ru.normno.rutubedownloader.presentation.home.HomeScreen
import ru.normno.rutubedownloader.presentation.home.HomeViewModel
import ru.normno.rutubedownloader.util.video.VideoManager

@Composable
@Preview
fun App() {
    KoinMultiplatformApplication(
        config = createKoinConfiguration()
    ) {
        MaterialTheme {
            val viewModel = koinViewModel<HomeViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            val videoManager = koinInject<VideoManager>()
            val localization = koinInject<Localization>()

            val languageIso by rememberStringSetting(
                key = "savedLanguageIso",
                defaultValue = Language.English.iso,
            )

            val selectedLanguage by derivedStateOf {
                Language.entries.first() { it.iso == languageIso }
            }

            localization.applyLanguage(languageIso)

            Scaffold(
                modifier = Modifier
                    .fillMaxSize(),
            ) { paddingValues ->
                HomeScreen(
                    state = state,
                    setVideoUrl = viewModel::setVideoUrl,
                    onSelectedVideoQuality = viewModel::onSelectedVideoQuality,
                    onDownloadVideo = viewModel::onDownloadVideo,
                    onGetVideo = viewModel::getVideoById,
                    onOpenVideo = { path ->
                        videoManager.openVideo(
                            path = path,
                        )
                    },
                    onShareVideo = viewModel::onShareVideo,
                    onDeleteVideo = viewModel::onDeleteFile,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                )
            }
        }
    }
}