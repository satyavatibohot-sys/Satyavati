package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AniStreamColorScheme = darkColorScheme(
    primary = FlameRed,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF4A1005),
    onPrimaryContainer = Color(0xFFFFCCBC),
    secondary = NeonCyan,
    onSecondary = Color(0xFF00363D),
    secondaryContainer = Color(0xFF004F58),
    onSecondaryContainer = Color(0xFF97F0FF),
    tertiary = ElectricGold,
    onTertiary = Color(0xFF3F2E00),
    background = DarkBackground,
    onBackground = TextPrimary,
    surface = DarkSurface,
    onSurface = TextPrimary,
    surfaceVariant = DarkSurfaceElevated,
    onSurfaceVariant = TextSecondary,
    outline = DarkBorder,
    outlineVariant = Color(0xFF1E2638)
)

@Composable
fun AniStreamTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AniStreamColorScheme,
        typography = Typography,
        content = content
    )
}
