package ru.normno.rutubedownloader.di

import io.ktor.client.HttpClient
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.normno.rutubedownloader.data.remote.KtorApiClient
import ru.normno.rutubedownloader.data.remote.api.RuTubeVideo
import ru.normno.rutubedownloader.data.remote.buildHttpClient
import ru.normno.rutubedownloader.data.remote.getHttpEngine
import ru.normno.rutubedownloader.data.repository.DownloaderRepositoryImpl
import ru.normno.rutubedownloader.domain.repository.DownloaderRepository
import ru.normno.rutubedownloader.presentation.home.HomeViewModel

object AppModule {
    fun initializeKoin() {
        startKoin {
            modules(
                networkModule,
                ktorApiClient,
                ruTubeVideo,
                downloaderRepository,
                homeViewModel,
            )
        }
    }

    private val homeViewModel = module {
        viewModel { HomeViewModel(get()) }
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

    private fun provideHttpClient(): HttpClient {
        return buildHttpClient(httpEngine)
    }

    private val httpEngine by lazy { getHttpEngine() }
}