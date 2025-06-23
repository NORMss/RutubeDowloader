package ru.normno.rutubedownloader.util.platform


actual class ShareLinkProvider {
    actual fun getSharedLink(): String? {
        return SharedLinkHandler.sharedLink
    }
}