package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AniStreamDao {
    // Watchlist
    @Query("SELECT * FROM watchlist ORDER BY updatedAt DESC")
    fun getAllWatchlist(): Flow<List<WatchlistEntity>>

    @Query("SELECT * FROM watchlist WHERE status = :status ORDER BY updatedAt DESC")
    fun getWatchlistByStatus(status: String): Flow<List<WatchlistEntity>>

    @Query("SELECT * FROM watchlist WHERE animeId = :animeId LIMIT 1")
    fun getWatchlistEntry(animeId: String): Flow<WatchlistEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateWatchlist(entry: WatchlistEntity)

    @Query("DELETE FROM watchlist WHERE animeId = :animeId")
    suspend fun removeFromWatchlist(animeId: String)

    @Query("UPDATE watchlist SET lastEpisodeWatched = :ep, progressSeconds = :progress, updatedAt = :time WHERE animeId = :animeId")
    suspend fun updateWatchProgress(animeId: String, ep: Int, progress: Int, time: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllWatchlist(entries: List<WatchlistEntity>)

    // Downloads
    @Query("SELECT * FROM downloads ORDER BY downloadedAt DESC")
    fun getAllDownloads(): Flow<List<DownloadEntity>>

    @Query("SELECT * FROM downloads WHERE id = :id LIMIT 1")
    suspend fun getDownloadById(id: String): DownloadEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDownload(download: DownloadEntity)

    @Query("DELETE FROM downloads WHERE id = :id")
    suspend fun deleteDownload(id: String)

    @Query("DELETE FROM downloads")
    suspend fun clearAllDownloads()

    // User Account
    @Query("SELECT * FROM user_account WHERE id = :id LIMIT 1")
    fun getUserAccount(id: String = "default_user"): Flow<UserAccountEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserAccount(user: UserAccountEntity)
}
