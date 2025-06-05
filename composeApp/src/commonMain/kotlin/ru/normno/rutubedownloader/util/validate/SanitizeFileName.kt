package ru.normno.rutubedownloader.util.validate

object SanitizeFileName {
    fun sanitizeFileName(name: String): String {
        return name
            .replace("[:\\\\/*\"?|<>']".toRegex(), "_")
            .trim()
    }
}