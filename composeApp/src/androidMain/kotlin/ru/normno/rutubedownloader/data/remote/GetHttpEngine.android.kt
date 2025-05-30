package ru.normno.rutubedownloader.data.remote

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

actual fun getHttpEngine(): HttpClientEngine = OkHttp.create()