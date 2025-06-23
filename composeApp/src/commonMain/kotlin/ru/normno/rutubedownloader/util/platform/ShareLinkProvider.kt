package ru.normno.rutubedownloader.util.platform

expect class ShareLinkProvider() {
    fun getSharedLink(): String?
}