package ru.normno.rutubedownloader.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Video(
    @SerialName("title")
    val title: String,
    @SerialName("thumbnail_url")
    val previewUrl: String,
    @SerialName("video_balancer")
    val videoUrl: VideoUrl,
)

@Serializable
data class VideoUrl(
    @SerialName("m3u8")
    val m3u8Playlist: String,
)