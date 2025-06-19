package ru.normno.rutubedownloader.domain

import java.util.Locale

actual class Localization {
    actual fun applyLanguage(iso: String) {
        val locale = Locale.forLanguageTag(iso)
        Locale.setDefault(locale)
    }
}