package ru.normno.rutubedownloader

import android.app.Application
import ru.normno.rutubedownloader.di.AppModule.initializeKoin

class RuTubeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin()
    }
}