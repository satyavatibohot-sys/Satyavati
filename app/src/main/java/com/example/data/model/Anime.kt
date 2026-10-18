package com.example.data.model

import androidx.annotation.DrawableRes
import com.example.R

enum class AudioTrack(val displayName: String, val nativeName: String, val tag: String) {
    HINDI("Hindi Dubbed", "हिन्दी डब", "HI"),
    JAPANESE("Japanese", "日本語 (Eng Sub)", "JA"),
    ENGLISH("English Dub", "English", "EN")
}

enum class VideoQuality(val label: String, val bitrate: String, val resolution: String) {
    Q1080P("1080p FHD", "6.2 Mbps", "1920x1080"),
    Q720P("720p HD", "3.1 Mbps", "1280x720"),
    Q480P("480p SD", "1.4 Mbps", "854x480"),
    AUTO("Auto Quality", "Dynamic", "Adaptive")
}

enum class WatchStatus(val title: String) {
    WATCHING("Watching"),
    PLAN_TO_WATCH("Plan to Watch"),
    COMPLETED("Completed"),
    FAVORITE("Favorites")
}

data class Anime(
    val id: String,
    val title: String,
    val titleHindi: String,
    val titleJapanese: String,
    val synopsis: String,
    val synopsisHindi: String,
    @DrawableRes val posterRes: Int,
    @DrawableRes val bannerRes: Int,
    val rating: Float,
    val releaseYear: Int,
    val totalEpisodes: Int,
    val genres: List<String>,
    val audioTracks: List<AudioTrack>,
    val voiceCastHindi: List<String>,
    val studio: String,
    val status: String = "Ongoing",
    val isFeatured: Boolean = false,
    val isTrending: Boolean = false,
    val isHindiExclusive: Boolean = true
)

data class Episode(
    val id: String,
    val animeId: String,
    val episodeNumber: Int,
    val title: String,
    val titleHindi: String,
    val durationSeconds: Int,
    val synopsis: String,
    val availableAudio: List<AudioTrack> = listOf(AudioTrack.HINDI, AudioTrack.JAPANESE, AudioTrack.ENGLISH),
    val downloadSizeBytes: Long = 345_000_000L // ~345 MB for 1080p
) {
    val durationFormatted: String
        get() {
            val minutes = durationSeconds / 60
            val seconds = durationSeconds % 60
            return "%d:%02d".format(minutes, seconds)
        }
}

data class UserAccount(
    val id: String = "user_default_1",
    val username: String = "AnimeVoyager",
    val email: String = "voyager@anistream.free",
    val isRegistered: Boolean = true,
    val syncCode: String = "ANI-7824",
    val avatarIndex: Int = 1,
    val memberSince: String = "October 2026",
    val isLifetimeFree: Boolean = true
)
