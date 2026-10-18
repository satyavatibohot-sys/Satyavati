package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.AuthDialog
import com.example.ui.components.SyncDialog
import com.example.ui.components.VideoPlayerView
import com.example.ui.navigation.AniBottomNav
import com.example.ui.screens.AnimeDetailScreen
import com.example.ui.screens.DownloadsScreen
import com.example.ui.screens.ExploreScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.WatchlistScreen
import com.example.ui.theme.AniStreamTheme
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.FlameRed
import com.example.ui.theme.FreeBadge
import com.example.ui.theme.HindiDubBadge
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.AnimeViewModel
import com.example.ui.viewmodel.AppScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AniStreamTheme {
                AniStreamApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AniStreamApp(
    viewModel: AnimeViewModel = viewModel()
) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val selectedAnime by viewModel.selectedAnime.collectAsState()
    val playerState by viewModel.playerState.collectAsState()
    val watchlist by viewModel.watchlistItems.collectAsState()
    val downloads by viewModel.downloadedItems.collectAsState()
    val userAccount by viewModel.userAccount.collectAsState()
    val showSyncDialog by viewModel.showSyncDialog.collectAsState()
    val showAuthDialog by viewModel.showAuthDialog.collectAsState()
    val notification by viewModel.notification.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(notification) {
        notification?.let {
            snackbarHostState.showSnackbar(it.message)
            viewModel.clearNotification()
        }
    }

    // Android System Back Navigation Handler
    BackHandler(enabled = playerState != null || selectedAnime != null || currentScreen != AppScreen.HOME) {
        when {
            playerState != null -> viewModel.closePlayer()
            selectedAnime != null -> viewModel.closeAnimeDetail()
            currentScreen != AppScreen.HOME -> viewModel.navigateTo(AppScreen.HOME)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = DarkBackground,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            if (playerState == null && selectedAnime == null) {
                Surface(
                    color = DarkSurface,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Brand Logo & Title
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_anistream_logo),
                                    contentDescription = "AniStream Logo",
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "Ani",
                                        color = TextPrimary,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                    Text(
                                        text = "Stream",
                                        color = FlameRed,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                                Text(
                                    text = "100% Free • All Anime",
                                    color = FreeBadge,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // Right Action: Hindi Dubbed Badge & Sync Button
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Hindi Dubbed indicator chip
                            Box(
                                modifier = Modifier
                                    .background(HindiDubBadge.copy(alpha = 0.2f), RoundedCornerShape(20.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.RecordVoiceOver,
                                        contentDescription = "Hindi Dub",
                                        tint = HindiDubBadge,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "हिन्दी Dubbed",
                                        color = HindiDubBadge,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            // Sync Devices Button
                            IconButton(
                                onClick = { viewModel.openSyncDialog() },
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(DarkBackground, CircleShape)
                                    .testTag("topbar_sync_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Sync,
                                    contentDescription = "Sync Watchlist",
                                    tint = NeonCyan,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        },
        bottomBar = {
            if (playerState == null) {
                AniBottomNav(
                    currentScreen = currentScreen,
                    watchlistCount = watchlist.size,
                    downloadsCount = downloads.size,
                    onTabSelected = { screen ->
                        viewModel.navigateTo(screen)
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Main Screen Routing
            when {
                selectedAnime != null -> {
                    AnimeDetailScreen(
                        anime = selectedAnime!!,
                        viewModel = viewModel,
                        onBack = { viewModel.closeAnimeDetail() }
                    )
                }

                else -> {
                    when (currentScreen) {
                        AppScreen.HOME -> {
                            HomeScreen(
                                viewModel = viewModel,
                                watchlist = watchlist,
                                onSelectAnime = { anime -> viewModel.selectAnime(anime) }
                            )
                        }

                        AppScreen.EXPLORE -> {
                            com.example.ui.screens.AnimeLibraryScreen(
                                viewModel = viewModel,
                                onSelectAnime = { anime -> viewModel.selectAnime(anime) }
                            )
                        }

                        AppScreen.WATCHLIST -> {
                            WatchlistScreen(
                                viewModel = viewModel,
                                onSelectAnime = { anime -> viewModel.selectAnime(anime) }
                            )
                        }

                        AppScreen.DOWNLOADS -> {
                            DownloadsScreen(
                                viewModel = viewModel
                            )
                        }

                        AppScreen.PROFILE -> {
                            ProfileScreen(
                                viewModel = viewModel
                            )
                        }
                    }
                }
            }

            // High-Quality Video Streaming Player Overlay
            playerState?.let { activePlayerState ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.95f))
                        .statusBarsPadding()
                        .testTag("player_overlay_container")
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        VideoPlayerView(
                            playerState = activePlayerState,
                            onTogglePlayPause = { viewModel.togglePlayPause() },
                            onSeek = { seconds -> viewModel.seekTo(seconds) },
                            onSkipForward = { viewModel.skipForward10() },
                            onSkipBackward = { viewModel.skipBackward10() },
                            onSkipIntro = { viewModel.skipIntro() },
                            onAudioTrackSelected = { track -> viewModel.setAudioTrack(track) },
                            onQualitySelected = { quality -> viewModel.setQuality(quality) },
                            onSpeedSelected = { speed -> viewModel.setSpeed(speed) },
                            onClose = { viewModel.closePlayer() }
                        )

                        // Episode Quick-Switch Tray below player
                        activePlayerState.anime?.let { anime ->
                            val episodes = viewModel.getEpisodes(anime.id)
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Season Episodes",
                                        color = TextPrimary,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Text(
                                        text = "Audio: ${activePlayerState.audioTrack.displayName}",
                                        color = HindiDubBadge,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                androidx.compose.foundation.lazy.LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(episodes.size) { index ->
                                        val ep = episodes[index]
                                        val isCurrent = ep.episodeNumber == activePlayerState.episode?.episodeNumber
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(if (isCurrent) FlameRed else DarkSurface)
                                                .clickable {
                                                    viewModel.startStreaming(anime, ep, activePlayerState.isOfflineMode)
                                                }
                                                .padding(horizontal = 14.dp, vertical = 10.dp)
                                                .testTag("player_ep_chip_${ep.episodeNumber}")
                                        ) {
                                            Text(
                                                text = "EP ${ep.episodeNumber}",
                                                color = Color.White,
                                                fontSize = 12.sp,
                                                fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Multi-Device Watchlist Sync Dialog
    if (showSyncDialog) {
        SyncDialog(
            userAccount = userAccount,
            onDismiss = { viewModel.closeSyncDialog() },
            onSyncWithCode = { code -> viewModel.syncWithCode(code) }
        )
    }

    // Free Account Registration & Sign-In Dialog
    if (showAuthDialog) {
        AuthDialog(
            initialUsername = userAccount?.username ?: "",
            initialEmail = userAccount?.email ?: "",
            onDismiss = { viewModel.closeAuthDialog() },
            onRegister = { username, email, avatarIndex ->
                viewModel.registerAccount(username, email, avatarIndex)
            }
        )
    }
}
