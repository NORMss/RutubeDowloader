package ru.normno.rutubedownloader

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.normno.rutubedownloader.di.AppModule.createKoinConfiguration

class RuTubeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}