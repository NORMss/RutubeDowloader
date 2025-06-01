package ru.normno.rutubedownloader.di

import io.ktor.client.HttpClient
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.normno.rutubedownloader.data.remote.KtorApiClient
import ru.normno.rutubedownloader.data.remote.api.RuTubeVideo
import ru.normno.rutubedownloader.data.remote.buildHttpClient
import ru.normno.rutubedownloader.data.remote.getHttpEngine
import ru.normno.rutubedownloader.presentation.home.HomeViewModel

object AppModule {
    fun initializeKoin() {
        startKoin {
            modules(appModules)
        }
    }

    private val appModules = module {
        viewModelOf(::HomeViewModel)
        provideHttpClient()
        ktorApiClient
        ruTubeVideo
    }

    private val ruTubeVideo = module {
        single<RuTubeVideo> { RuTubeVideo(get()) }
    }

    private val ktorApiClient = module {
        single<KtorApiClient> { KtorApiClient(get()) }
    }

    private fun provideHttpClient(): HttpClient {
        return buildHttpClient(httpEngine)
    }

    private val httpEngine by lazy { getHttpEngine() }
}