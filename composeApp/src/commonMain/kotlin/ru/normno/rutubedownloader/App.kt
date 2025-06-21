@file:OptIn(KoinExperimentalAPI::class, ExperimentalMaterial3Api::class)

package ru.normno.rutubedownloader

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.burnoo.compose.remembersetting.rememberStringSetting
import org.jetbrains.compose.resources.stringResource
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
import rutubedownloader.composeapp.generated.resources.Res
import rutubedownloader.composeapp.generated.resources.app_name

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

            var languageIso by rememberStringSetting(
                key = "savedLanguageIso",
                defaultValue = Language.English.iso,
            )

            val selectedLanguage by remember {
                derivedStateOf {
                    Language.entries.first { it.iso == languageIso }
                }
            }

            var isShowAboutAppDialog by remember {
                mutableStateOf(false)
            }

            LaunchedEffect(selectedLanguage, Unit) {
                localization.applyLanguage(languageIso)
                println("localization.applyLanguage")
            }

            Scaffold(
                modifier = Modifier
                    .fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = stringResource(Res.string.app_name)
                            )
                        },
                        actions = {
                            IconButton(
                                onClick = {
                                    languageIso =
                                        if (selectedLanguage == Language.Russian) Language.English.iso
                                        else Language.Russian.iso
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Language,
                                    contentDescription = null,
                                )
                            }
                        }
                    )
                }
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