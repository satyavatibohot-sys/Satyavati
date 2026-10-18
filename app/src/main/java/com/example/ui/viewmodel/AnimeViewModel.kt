package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.DownloadEntity
import com.example.data.local.UserAccountEntity
import com.example.data.local.WatchlistEntity
import com.example.data.model.Anime
import com.example.data.model.AudioTrack
import com.example.data.model.Episode
import com.example.data.model.VideoQuality
import com.example.data.model.WatchStatus
import com.example.data.repository.AnimeRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class AppScreen {
    HOME,
    EXPLORE,
    WATCHLIST,
    DOWNLOADS,
    PROFILE
}

data class PlayerState(
    val anime: Anime? = null,
    val episode: Episode? = null,
    val isPlaying: Boolean = false,
    val currentPositionSeconds: Int = 0,
    val totalDurationSeconds: Int = 1440,
    val audioTrack: AudioTrack = AudioTrack.HINDI,
    val quality: VideoQuality = VideoQuality.Q1080P,
    val playbackSpeed: Float = 1.0f,
    val isOfflineMode: Boolean = false,
    val showControls: Boolean = true
)

data class UiNotification(
    val id: Long = System.currentTimeMillis(),
    val message: String
)

class AnimeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AnimeRepository

    init {
        val database = AppDatabase.getDatabase(application)
        repository = AnimeRepository(database.aniStreamDao())
        viewModelScope.launch {
            repository.populateInitialDataIfEmpty()
        }
    }

    // Navigation & Screen States
    private val _currentScreen = MutableStateFlow(AppScreen.HOME)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val _selectedAnime = MutableStateFlow<Anime?>(null)
    val selectedAnime: StateFlow<Anime?> = _selectedAnime.asStateFlow()

    // Player State
    private val _playerState = MutableStateFlow<PlayerState?>(null)
    val playerState: StateFlow<PlayerState?> = _playerState.asStateFlow()

    private var playbackTickerJob: Job? = null

    // Search & Filter
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedGenre = MutableStateFlow("All")
    val selectedGenre: StateFlow<String> = _selectedGenre.asStateFlow()

    private val _onlyHindiDubbed = MutableStateFlow(false)
    val onlyHindiDubbed: StateFlow<Boolean> = _onlyHindiDubbed.asStateFlow()

    // Watchlist & Downloads from Room
    val watchlistItems: StateFlow<List<WatchlistEntity>> = repository.getAllWatchlist()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val downloadedItems: StateFlow<List<DownloadEntity>> = repository.getAllDownloads()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val userAccount: StateFlow<UserAccountEntity?> = repository.getUserAccount()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    // UI Dialogs
    private val _showSyncDialog = MutableStateFlow(false)
    val showSyncDialog: StateFlow<Boolean> = _showSyncDialog.asStateFlow()

    private val _showAuthDialog = MutableStateFlow(false)
    val showAuthDialog: StateFlow<Boolean> = _showAuthDialog.asStateFlow()

    private val _notification = MutableStateFlow<UiNotification?>(null)
    val notification: StateFlow<UiNotification?> = _notification.asStateFlow()

    // Download in-progress map (episodeId -> progress 0..100)
    private val _activeDownloads = MutableStateFlow<Map<String, Int>>(emptyMap())
    val activeDownloads: StateFlow<Map<String, Int>> = _activeDownloads.asStateFlow()

    // Catalog Access
    val featuredAnime: List<Anime> get() = repository.getFeaturedAnime()
    val allAnime: List<Anime> get() = repository.getAllAnime()

    fun getFilteredCatalog(): List<Anime> {
        return repository.searchAnime(_searchQuery.value, _selectedGenre.value, _onlyHindiDubbed.value)
    }

    fun getEpisodes(animeId: String): List<Episode> = repository.getEpisodesForAnime(animeId)

    // Navigation Actions
    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
        _selectedAnime.value = null
    }

    fun selectAnime(anime: Anime) {
        _selectedAnime.value = anime
    }

    fun closeAnimeDetail() {
        _selectedAnime.value = null
    }

    // Video Player Actions
    fun startStreaming(anime: Anime, episode: Episode, isOffline: Boolean = false) {
        val initialSeconds = 0
        _playerState.value = PlayerState(
            anime = anime,
            episode = episode,
            isPlaying = true,
            currentPositionSeconds = initialSeconds,
            totalDurationSeconds = episode.durationSeconds,
            audioTrack = AudioTrack.HINDI,
            quality = VideoQuality.Q1080P,
            isOfflineMode = isOffline,
            showControls = true
        )
        startPlaybackTicker()

        // Update Room watch progress
        viewModelScope.launch {
            repository.updateWatchProgress(anime.id, episode.episodeNumber, initialSeconds, episode.durationSeconds)
        }
    }

    fun playOfflineDownload(download: DownloadEntity) {
        val anime = repository.getAnimeById(download.animeId)
        val episode = Episode(
            id = download.id,
            animeId = download.animeId,
            episodeNumber = download.episodeNumber,
            title = download.episodeTitle,
            titleHindi = download.episodeTitleHindi,
            durationSeconds = download.durationSeconds,
            synopsis = "Playing stored offline episode without internet connection.",
            downloadSizeBytes = download.fileSizeBytes
        )
        if (anime != null) {
            startStreaming(anime, episode, isOffline = true)
        }
    }

    fun togglePlayPause() {
        _playerState.update { current ->
            if (current == null) null
            else {
                val newPlaying = !current.isPlaying
                if (newPlaying) startPlaybackTicker() else playbackTickerJob?.cancel()
                current.copy(isPlaying = newPlaying)
            }
        }
    }

    fun seekTo(seconds: Int) {
        _playerState.update { current ->
            if (current == null) null
            else {
                val clamped = seconds.coerceIn(0, current.totalDurationSeconds)
                viewModelScope.launch {
                    current.anime?.let { anime ->
                        current.episode?.let { ep ->
                            repository.updateWatchProgress(anime.id, ep.episodeNumber, clamped, current.totalDurationSeconds)
                        }
                    }
                }
                current.copy(currentPositionSeconds = clamped)
            }
        }
    }

    fun skipForward10() {
        val current = _playerState.value ?: return
        seekTo(current.currentPositionSeconds + 10)
    }

    fun skipBackward10() {
        val current = _playerState.value ?: return
        seekTo(current.currentPositionSeconds - 10)
    }

    fun skipIntro() {
        val current = _playerState.value ?: return
        seekTo((current.currentPositionSeconds + 85).coerceAtMost(current.totalDurationSeconds))
        showNotification("Skipped 85s Intro!")
    }

    fun setAudioTrack(track: AudioTrack) {
        _playerState.update { it?.copy(audioTrack = track) }
        showNotification("Audio switched to ${track.displayName}")
    }

    fun setQuality(quality: VideoQuality) {
        _playerState.update { it?.copy(quality = quality) }
        showNotification("Quality set to ${quality.label}")
    }

    fun setSpeed(speed: Float) {
        _playerState.update { it?.copy(playbackSpeed = speed) }
    }

    fun closePlayer() {
        playbackTickerJob?.cancel()
        _playerState.value = null
    }

    private fun startPlaybackTicker() {
        playbackTickerJob?.cancel()
        playbackTickerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _playerState.update { current ->
                    if (current != null && current.isPlaying) {
                        val nextSec = current.currentPositionSeconds + 1
                        if (nextSec >= current.totalDurationSeconds) {
                            current.copy(currentPositionSeconds = current.totalDurationSeconds, isPlaying = false)
                        } else {
                            current.copy(currentPositionSeconds = nextSec)
                        }
                    } else current
                }
            }
        }
    }

    // Search and Filter Handlers
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setGenre(genre: String) {
        _selectedGenre.value = genre
    }

    fun toggleHindiOnly() {
        _onlyHindiDubbed.value = !_onlyHindiDubbed.value
    }

    // Watchlist Operations
    fun toggleWatchlist(anime: Anime, status: WatchStatus = WatchStatus.WATCHING) {
        viewModelScope.launch {
            val existing = watchlistItems.value.find { it.animeId == anime.id }
            if (existing != null) {
                repository.removeFromWatchlist(anime.id)
                showNotification("Removed from watchlist")
            } else {
                repository.addToWatchlist(anime, status)
                showNotification("Added to ${status.title} (Synced across devices)")
            }
        }
    }

    fun removeFromWatchlist(animeId: String) {
        viewModelScope.launch {
            repository.removeFromWatchlist(animeId)
            showNotification("Removed from watchlist")
        }
    }

    // Downloads Management
    fun triggerDownload(anime: Anime, episode: Episode, quality: VideoQuality = VideoQuality.Q1080P, audio: AudioTrack = AudioTrack.HINDI) {
        val downloadId = "${anime.id}_${episode.episodeNumber}"
        if (_activeDownloads.value.containsKey(downloadId)) {
            showNotification("Download already in progress")
            return
        }

        viewModelScope.launch {
            // Animate download progress
            _activeDownloads.update { it + (downloadId to 0) }
            showNotification("Downloading Ep ${episode.episodeNumber} in ${audio.displayName} (${quality.label})...")

            for (progress in 10..100 step 20) {
                delay(300)
                _activeDownloads.update { it + (downloadId to progress) }
            }

            repository.downloadEpisode(anime, episode, quality, audio)
            _activeDownloads.update { it - downloadId }
            showNotification("Ep ${episode.episodeNumber} downloaded for offline viewing!")
        }
    }

    fun deleteDownload(downloadId: String) {
        viewModelScope.launch {
            repository.deleteDownload(downloadId)
            showNotification("Episode removed from offline downloads")
        }
    }

    fun clearAllDownloads() {
        viewModelScope.launch {
            repository.clearAllDownloads()
            showNotification("All offline downloads cleared")
        }
    }

    // Multi-Device Watchlist Sync
    fun openSyncDialog() {
        _showSyncDialog.value = true
    }

    fun closeSyncDialog() {
        _showSyncDialog.value = false
    }

    fun syncWithCode(code: String) {
        viewModelScope.launch {
            val result = repository.syncWithDeviceCode(code)
            if (result.isSuccess) {
                showNotification("Successfully synchronized ${result.getOrNull()} items with device $code!")
                _showSyncDialog.value = false
            } else {
                showNotification(result.exceptionOrNull()?.message ?: "Failed to sync device")
            }
        }
    }

    // Account & Registration
    fun openAuthDialog() {
        _showAuthDialog.value = true
    }

    fun closeAuthDialog() {
        _showAuthDialog.value = false
    }

    fun registerAccount(username: String, email: String, avatarIndex: Int) {
        viewModelScope.launch {
            repository.registerUser(username, email, avatarIndex)
            _showAuthDialog.value = false
            showNotification("Welcome $username! Free Lifetime All-Anime Pass Activated.")
        }
    }

    fun showNotification(msg: String) {
        _notification.value = UiNotification(message = msg)
    }

    fun clearNotification() {
        _notification.value = null
    }
}
