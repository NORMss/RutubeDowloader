package ru.normno.rutubedownloader.di

import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.normno.rutubedownloader.presentation.home.HomeViewModel

object AppModule {
    fun initializeKoin() {
        startKoin {
            modules(appModules)
        }
    }

    private val appModules = module {
        viewModelOf(::HomeViewModel)
    }
}