@file:OptIn(KoinExperimentalAPI::class, ExperimentalMaterial3Api::class)

package ru.normno.rutubedownloader

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import ru.normno.rutubedownloader.presentation.home.component.AboutAppDialog
import ru.normno.rutubedownloader.util.platform.PlatformConfig
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

            val selectedLanguage by derivedStateOf {
                Language.entries.first { it.iso == languageIso }
            }

            localization.applyLanguage(languageIso)

            var isShowAboutAppDialog by remember {
                mutableStateOf(false)
            }

            if (isShowAboutAppDialog) {
                AboutAppDialog(
                    onClose = {
                        isShowAboutAppDialog = false
                    }
                )
            }

            key(languageIso) {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                navigationIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                actionIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            ),
                            title = {
                                Row(
                                    verticalAlignment = Alignment.Bottom,
                                ) {
                                    Text(
                                        text = stringResource(Res.string.app_name)
                                    )
                                    Spacer(
                                        modifier = Modifier
                                            .width(4.dp),
                                    )
                                    Text(
                                        text = PlatformConfig.versionCode,
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier
                                            .background(
                                                MaterialTheme.colorScheme.tertiaryContainer.copy(
                                                    alpha = 0.8f
                                                ),
                                                RoundedCornerShape(4.dp),
                                            )
                                            .padding(vertical = 2.dp, horizontal = 4.dp),
                                        color = MaterialTheme.colorScheme.onTertiaryContainer
                                    )
                                }
                            },
                            actions = {
                                IconButton(
                                    onClick = {
                                        languageIso =
                                            if (languageIso == Language.Russian.iso) Language.English.iso
                                            else Language.Russian.iso
                                        localization.applyLanguage(languageIso)
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Language,
                                        contentDescription = null,
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        isShowAboutAppDialog = true
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Info,
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
}