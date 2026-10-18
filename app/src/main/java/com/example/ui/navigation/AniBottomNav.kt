package com.example.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.DownloadDone
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.FlameRed
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.AppScreen

sealed class NavItem(
    val screen: AppScreen,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val tag: String
) {
    object Home : NavItem(AppScreen.HOME, "Home", Icons.Filled.Home, Icons.Outlined.Home, "nav_home")
    object Library : NavItem(AppScreen.EXPLORE, "Library", Icons.Filled.Explore, Icons.Outlined.Explore, "nav_library")
    object Watchlist : NavItem(AppScreen.WATCHLIST, "Watchlist", Icons.Filled.Bookmark, Icons.Outlined.BookmarkBorder, "nav_watchlist")
    object Downloads : NavItem(AppScreen.DOWNLOADS, "Downloads", Icons.Filled.DownloadDone, Icons.Outlined.DownloadDone, "nav_downloads")
    object Profile : NavItem(AppScreen.PROFILE, "Profile", Icons.Filled.Person, Icons.Outlined.Person, "nav_profile")
}

@Composable
fun AniBottomNav(
    currentScreen: AppScreen,
    watchlistCount: Int,
    downloadsCount: Int,
    onTabSelected: (AppScreen) -> Unit
) {
    val items = listOf(
        NavItem.Home,
        NavItem.Library,
        NavItem.Watchlist,
        NavItem.Downloads,
        NavItem.Profile
    )

    NavigationBar(
        containerColor = DarkSurface,
        tonalElevation = 8.dp,
        modifier = Modifier.testTag("bottom_nav_bar")
    ) {
        items.forEach { item ->
            val isSelected = currentScreen == item.screen
            val badgeCount = when (item) {
                NavItem.Watchlist -> watchlistCount
                NavItem.Downloads -> downloadsCount
                else -> 0
            }

            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelected(item.screen) },
                icon = {
                    if (badgeCount > 0) {
                        BadgedBox(badge = {
                            Badge(
                                containerColor = FlameRed,
                                contentColor = Color.White
                            ) {
                                Text("$badgeCount", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }) {
                            Icon(
                                imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    } else {
                        Icon(
                            imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.title,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = FlameRed,
                    selectedTextColor = FlameRed,
                    indicatorColor = FlameRed.copy(alpha = 0.15f),
                    unselectedIconColor = TextMuted,
                    unselectedTextColor = TextMuted
                ),
                modifier = Modifier.testTag(item.tag)
            )
        }
    }
}
