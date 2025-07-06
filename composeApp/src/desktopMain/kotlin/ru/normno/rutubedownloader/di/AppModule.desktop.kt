package ru.normno.rutubedownloader.di

import org.koin.core.module.Module
import org.koin.dsl.module
import ru.normno.rutubedownloader.data.locale.disk.DiskUsageImplDesktop
import ru.normno.rutubedownloader.data.locale.localiztion.Localization
import ru.normno.rutubedownloader.domain.local.disk.DiskUsage
import ru.normno.rutubedownloader.util.video.VideoManager

actual val videoManagerModule: Module = module {
    single<VideoManager> { VideoManager() }
}

actual val localizationModule: Module = module {
    single<Localization> { Localization() }
}
actual val diskUsageModule: Module = module {
    single<DiskUsage> { DiskUsageImplDesktop() }
}