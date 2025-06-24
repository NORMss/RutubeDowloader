package ru.normno.rutubedownloader.util.errorhendling

sealed class Result<D, E : Error>(
    val data: D? = null,
    val error: ru.normno.rutubedownloader.util.errorhendling.Error? = null,
) {
    class Succes<D>(
        data: D?
    ) : Result<D, ru.normno.rutubedownloader.util.errorhendling.Error>(data)

    class Error<D>(
        error: ru.normno.rutubedownloader.util.errorhendling.Error
    ) : Result<D, ru.normno.rutubedownloader.util.errorhendling.Error>(null, error)
}