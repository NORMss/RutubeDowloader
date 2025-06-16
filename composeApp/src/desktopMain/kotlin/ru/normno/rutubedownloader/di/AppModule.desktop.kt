package ru.normno.rutubedownloader.di

import org.koin.core.module.Module
import org.koin.dsl.module
import ru.normno.rutubedownloader.util.video.VideoManager

actual val videoManagerModule: Module = module {
    single<VideoManager> { VideoManager() }
}

actual val localizationModule: Module
    get() = TODO("Not yet implemented")