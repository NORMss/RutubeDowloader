package ru.normno.rutubedownloader.util.video

object ParseM3U8Playlist {
    data class VideoQuality(
        val url: String,
        val resolution: String,
        val bandwidth: Int,
        val codecs: String,
    )


    fun parseM3U8Playlist(playlistContent: String): List<VideoQuality> {
        val qualities = mutableListOf<VideoQuality>()
        val lines = playlistContent.lines()

        var currentBandwidth = 0
        var currentResolution = ""
        var currentCodecs = ""

        for (line in lines) {
            when {
                line.startsWith("#EXT-X-STREAM-INF:") -> {
                    currentBandwidth = extractAttribute(line, "BANDWIDTH")?.toIntOrNull() ?: 0
                    currentResolution = extractAttribute(line, "RESOLUTION") ?: ""
                    currentCodecs = extractAttribute(line, "CODECS")?.removeSurrounding("\"") ?: ""
                }
                line.startsWith("http") -> {
                    if (currentResolution.isNotEmpty()) {
                        qualities.add(
                            VideoQuality(
                                url = line,
                                resolution = currentResolution,
                                bandwidth = currentBandwidth,
                                codecs = currentCodecs
                            )
                        )
                    }
                }
            }
        }

        return qualities.sortedByDescending { it.bandwidth }
    }

    private fun extractAttribute(line: String, attributeName: String): String? {
        val regex = "$attributeName=([^,]+)".toRegex()
        return regex.find(line)?.groupValues?.get(1)
    }
}