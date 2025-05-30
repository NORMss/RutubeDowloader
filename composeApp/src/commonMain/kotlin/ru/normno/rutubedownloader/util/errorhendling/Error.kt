package ru.normno.rutubedownloader.util.errorhendling

sealed interface Error {
    enum class Remote : Error {
        CLIENT_ERROR,
        REDIRECTION_ERROR,
        SERVER_ERROR,
        NO_INTERNET_ERROR,
        SERIALIZATION_ERROR,
        UNKNOWN,
    }

    enum class Local : Error {
        NOT_FOUND,
        IO_EXCEPTION,
        UNKNOWN,
    }
}

data class RemoteErrorWithCode(
    val error: Error,
    val code: Int = 0,
) : Error