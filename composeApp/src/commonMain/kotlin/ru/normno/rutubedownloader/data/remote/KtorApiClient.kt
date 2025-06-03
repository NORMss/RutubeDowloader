package ru.normno.rutubedownloader.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.onDownload
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.utils.io.CancellationException
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.SerializationException
import ru.normno.rutubedownloader.util.Constats.BASE_URL
import ru.normno.rutubedownloader.util.errorhendling.Error
import ru.normno.rutubedownloader.util.errorhendling.RemoteErrorWithCode
import ru.normno.rutubedownloader.util.errorhendling.Result
import java.net.UnknownHostException

class KtorApiClient(
    val httpClient: HttpClient,
) {
    suspend inline fun <reified R : Any> get(
        route: String,
        parameters: Map<String, Any> = emptyMap(),
        headers: Map<String, Any> = emptyMap(),
    ): Result<R, Error> {
        return saveCall {
            httpClient.get {
                url("$baseUrl$route")

                parameters.forEach { (key, value) ->
                    parameter(key, value)
                }

                headers.forEach { (key, value) ->
                    header(key, value)
                }

            }
        }
    }

    suspend inline fun <reified R : Any> post(
        route: String,
        body: Map<String, Any> = emptyMap(),
        parameters: Map<String, Any> = emptyMap(),
        headers: Map<String, Any> = emptyMap(),
    ): Result<R, Error> {
        return saveCall {
            httpClient.post {
                url("$baseUrl$route")

                contentType(ContentType.Application.Json)

                parameters.forEach { (key, value) ->
                    parameter(key, value)
                }

                setBody(body)

                headers.forEach { (key, value) ->
                    header(key, value)
                }

            }
        }
    }

    suspend fun downloadFile(
        fileUrl: String,
        onProgress: (Float) -> Unit = {}
    ): Result<ByteArray, Error> {
        return saveCall<ByteArray> {
            httpClient.get(fileUrl) {
                onDownload { bytesSentTotal, contentLength ->
                    if (contentLength != null) {
                        onProgress(bytesSentTotal.toFloat() / contentLength)
                    }
                }
            }
        }
    }

    suspend fun downloadHlsStream(
        playlistUrl: String,
        onProgress: (Float) -> Unit = {}
    ): Result<ByteArray, Error> = coroutineScope {

        val playlist = httpClient.get(playlistUrl).bodyAsText()


        val lines = playlist.lines()
        val segmentUrls = lines
            .filter { it.isNotBlank() && !it.startsWith("#") }

        if (segmentUrls.isEmpty()) {
            return@coroutineScope Result.Error(
                RemoteErrorWithCode(Error.Remote.UNKNOWN)
            )
        }

        val baseUrl = playlistUrl.substringBeforeLast("/") + "/"

        val total = segmentUrls.size
        val downloadedSegments = mutableListOf<Deferred<ByteArray?>>()

        segmentUrls.forEachIndexed { index, relativePath ->
            val segmentUrl = if (relativePath.startsWith("http")) relativePath else baseUrl + relativePath

            val deferred = async(Dispatchers.IO) {
                val segmentResult = downloadFile(segmentUrl)
                if (segmentResult is Result.Success) {
                    onProgress((index + 1).toFloat() / total)
                    segmentResult.data
                } else {
                    null
                }
            }
            downloadedSegments.add(deferred)
        }

        val resultBytes = downloadedSegments.awaitAll()

        if (resultBytes.any { it == null }) {
            return@coroutineScope Result.Error(
                RemoteErrorWithCode(Error.Remote.UNKNOWN)
            )
        }

        val merged = resultBytes.filterNotNull().reduce { acc, bytes -> acc + bytes }

        Result.Success(merged)
    }

    suspend inline fun <reified R> saveCall(
        execute: () -> HttpResponse,
    ): Result<R, Error> {
        val response = try {
            execute()
        } catch (e: UnknownHostException) {
            e.printStackTrace()
            return Result.Error(
                RemoteErrorWithCode(Error.Remote.NO_INTERNET_ERROR)
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            return Result.Error(
                RemoteErrorWithCode(Error.Remote.SERIALIZATION_ERROR)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            return Result.Error(
                RemoteErrorWithCode(Error.Remote.UNKNOWN)
            )
        }
        return responseToResult(response)
    }


    suspend inline fun <reified R> responseToResult(response: HttpResponse): Result<R, Error> {
        return when (response.status.value) {
            in 200..299 -> Result.Success(
                response.body<R>()
            )

            in 300..308 -> Result.Error(
                RemoteErrorWithCode(
                    Error.Remote.REDIRECTION_ERROR,
                    response.status.value,
                )
            )

            in 400..499 -> Result.Error(
                RemoteErrorWithCode(
                    Error.Remote.CLIENT_ERROR,
                    response.status.value,
                )
            )

            in 500..599 -> Result.Error(
                RemoteErrorWithCode(
                    Error.Remote.SERVER_ERROR,
                    response.status.value,
                )
            )

            else -> Result.Error(
                RemoteErrorWithCode(
                    Error.Remote.UNKNOWN,
                    response.status.value,
                )
            )
        }
    }


    val baseUrl = BASE_URL
}