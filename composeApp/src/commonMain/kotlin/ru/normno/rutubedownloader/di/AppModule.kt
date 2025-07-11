package ru.normno.rutubedownloader.di

import io.github.vinceglb.filekit.FileKit
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.KoinConfiguration
import org.koin.dsl.module
import ru.normno.rutubedownloader.data.locale.disk.PlatformDiskUsage
import ru.normno.rutubedownloader.data.remote.KtorApiClient
import ru.normno.rutubedownloader.data.remote.api.RuTubeVideo
import ru.normno.rutubedownloader.data.remote.buildHttpClient
import ru.normno.rutubedownloader.data.remote.getHttpEngine
import ru.normno.rutubedownloader.data.repository.DownloaderRepositoryImpl
import ru.normno.rutubedownloader.data.repository.FileRepositoryImpl
import ru.normno.rutubedownloader.domain.repository.DownloaderRepository
import ru.normno.rutubedownloader.domain.repository.FileRepository
import ru.normno.rutubedownloader.presentation.home.HomeViewModel
import ru.normno.rutubedownloader.util.platform.ShareLinkProvider

expect val videoManagerModule: Module

expect val localizationModule: Module

expect val diskUsageModule: Module

object AppModule {

    fun createKoinConfiguration(): KoinConfiguration {
        return KoinConfiguration {
            modules(
                networkModule,
                localModule,
                ktorApiClient,
                ruTubeVideo,
                downloaderRepository,
                fileRepository,
                homeViewModel,
                localizationModule,
                videoManagerModule,
                shareLinkProvider,
                platformDiskUsage,
            )
        }
    }

    private val homeViewModel = module {
        viewModel { HomeViewModel(get(), get(), get()) }
    }

    private val platformDiskUsage = module {
        single<PlatformDiskUsage> { PlatformDiskUsage() }
    }

    private val fileRepository = module {
        single<FileRepository> { FileRepositoryImpl(get(), get()) }
    }

    private val downloaderRepository = module {
        single<DownloaderRepository> { DownloaderRepositoryImpl(get()) }
    }

    private val ruTubeVideo = module {
        single<RuTubeVideo> { RuTubeVideo(get()) }
    }

    private val ktorApiClient = module {
        single<KtorApiClient> { KtorApiClient(get()) }
    }

    private val networkModule = module {
        single { provideHttpClient() }
    }

    private val localModule = module {
        single { fileKit }
    }

    private val shareLinkProvider = module {
        single { ShareLinkProvider() }
    }

    private fun provideHttpClient(): HttpClient {
        return buildHttpClient(httpEngine)
    }

    private val fileKit = FileKit

    private val httpEngine by lazy { getHttpEngine() }
}