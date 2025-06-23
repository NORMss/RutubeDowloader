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
        val seenResolutions = mutableSetOf<String>()
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
                    if (currentResolution.isNotEmpty() && isResolutionAllowed(currentResolution)) {
                        val verticalResolution = currentResolution.split("x").getOrNull(1)?.toIntOrNull() ?: 0
                        if (currentResolution !in seenResolutions) {
                            qualities.add(
                                VideoQuality(
                                    url = line,
                                    resolution = currentResolution,
                                    bandwidth = currentBandwidth,
                                    codecs = currentCodecs
                                )
                            )
                            seenResolutions.add(currentResolution)
                        }
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

    private fun isResolutionAllowed(resolution: String): Boolean {
        val vertical = resolution.split("x").getOrNull(1)?.toIntOrNull() ?: return false
        return vertical >= 400
    }
}