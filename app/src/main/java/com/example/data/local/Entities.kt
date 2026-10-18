package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watchlist")
data class WatchlistEntity(
    @PrimaryKey val animeId: String,
    val animeTitle: String,
    val animeTitleHindi: String,
    val posterRes: Int,
    val status: String, // WATCHING, PLAN_TO_WATCH, COMPLETED, FAVORITE
    val lastEpisodeWatched: Int = 1,
    val progressSeconds: Int = 0,
    val totalDurationSeconds: Int = 1440,
    val audioTrack: String = "HINDI",
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "downloads")
data class DownloadEntity(
    @PrimaryKey val id: String, // animeId_episodeNumber
    val animeId: String,
    val animeTitle: String,
    val episodeNumber: Int,
    val episodeTitle: String,
    val episodeTitleHindi: String,
    val posterRes: Int,
    val quality: String, // Q1080P, Q720P, Q480P
    val audioTrack: String, // HINDI, JAPANESE, ENGLISH
    val fileSizeBytes: Long,
    val durationSeconds: Int,
    val downloadProgress: Float = 1.0f,
    val isCompleted: Boolean = true,
    val downloadedAt: Long = System.currentTimeMillis(),
    val localUri: String = "file:///storage/emulated/0/AniStream/offline_episodes/"
)

@Entity(tableName = "user_account")
data class UserAccountEntity(
    @PrimaryKey val id: String = "default_user",
    val username: String = "AnimeVoyager",
    val email: String = "voyager@anistream.free",
    val isRegistered: Boolean = true,
    val syncCode: String = "ANI-7824",
    val avatarIndex: Int = 1,
    val memberSince: String = "October 2026",
    val isLifetimeFree: Boolean = true,
    val lastSyncTime: Long = System.currentTimeMillis()
)
