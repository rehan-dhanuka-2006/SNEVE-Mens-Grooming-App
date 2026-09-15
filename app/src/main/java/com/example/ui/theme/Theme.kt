package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SneveDarkColorScheme =
  darkColorScheme(
    primary = SneveRed,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF4A0407),
    onPrimaryContainer = SneveRedLight,
    secondary = SneveSilver,
    onSecondary = Color(0xFF111318),
    secondaryContainer = Color(0xFF232731),
    onSecondaryContainer = Color(0xFFF3F4F6),
    tertiary = SneveGold,
    onTertiary = Color(0xFF3B2702),
    tertiaryContainer = Color(0xFF5A3D06),
    onTertiaryContainer = Color(0xFFFEF3C7),
    background = SneveBackground,
    onBackground = SneveTextPrimary,
    surface = SneveSurface,
    onSurface = SneveTextPrimary,
    surfaceVariant = SneveSurfaceElevated,
    onSurfaceVariant = SneveTextSecondary,
    outline = SneveBorder,
    outlineVariant = SneveBorderSubtle,
    error = SneveRed,
    onError = Color.White
  )

private val SneveLightColorScheme =
  lightColorScheme(
    primary = SneveRedDark,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFE5E5),
    onPrimaryContainer = Color(0xFF80050B),
    secondary = Color(0xFF1E293B),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFF1F5F9),
    onSecondaryContainer = Color(0xFF0F172A),
    tertiary = Color(0xFFD97706),
    onTertiary = Color.White,
    background = Color(0xFF0A0B0E), // Keep luxury dark as standard
    onBackground = Color(0xFFF8FAFC),
    surface = Color(0xFF12141A),
    onSurface = Color(0xFFF8FAFC),
    surfaceVariant = Color(0xFF1C1E26),
    onSurfaceVariant = Color(0xFF94A3B8),
    outline = SneveBorder,
    outlineVariant = SneveBorderSubtle,
    error = SneveRed,
    onError = Color.White
  )

@Composable
fun SneveTheme(
  darkTheme: Boolean = true, // Default to luxury dark for SNEVE branding
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) SneveDarkColorScheme else SneveLightColorScheme
  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = true,
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  SneveTheme(darkTheme = darkTheme, content = content)
}

