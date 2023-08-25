package com.example.dailycheckquestboard.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

data class ExtendedColorScheme(
    val colorScheme: ColorScheme,
    val checkboxWork: Color,
    val checkboxPhysical: Color,
    val checkboxSocial: Color
)

val darkExtendedColorScheme = ExtendedColorScheme(
    colorScheme = darkColorScheme(
        primary = Color(0xFFBB86FC), // A type of purple.
        onPrimary = Color(0xFF000000), // Typically black or near-black for dark themes.
        primaryContainer = Color(0xFF3700B3),
        onPrimaryContainer = Color(0xFFBB86FC),

        secondary = Color(0xFF03DAC6), // A type of teal.
        onSecondary = Color(0xFF000000),
        secondaryContainer = Color(0xFF121212), // Very dark gray.
        onSecondaryContainer = Color(0xFF03DAC6),

        tertiary = Color(0xFFCF6679), // A type of rose.
        onTertiary = Color(0xFF000000),
        tertiaryContainer = Color(0xFF121212),
        onTertiaryContainer = Color(0xFFCF6679),

        background = Color(0xFF121212),
        onBackground = Color(0xFFFFFFFF), // Typically white or near-white for dark themes.

        surface = Color(0xFF333333), // Slightly lighter than background.
        onSurface = Color(0xFFFFFFFF),
        surfaceVariant = Color(0xFF242424), // Slight variation to the surface color.
        onSurfaceVariant = Color(0xFFFFFFFF),

        inverseSurface = Color(0xFFBB86FC),
        inverseOnSurface = Color(0xFF000000),

        error = Color(0xFFCF6679), // Using rose color for errors.
        onError = Color(0xFFFFFFFF),
        errorContainer = Color(0xFF121212),
        onErrorContainer = Color(0xFFCF6679),

        outline = Color(0xFF555555), // Medium gray.
        outlineVariant = Color(0xFF444444), // Slightly darker gray.

        scrim = Color(0xAA121212) // Scrim with some transparency
    ),
    checkboxWork = Color(0xFF8C8C8C),       // Even lighter gray from 6E6E6E
    checkboxPhysical = Color(0xFF40854A),   // Lightened green from 276738
    checkboxSocial = Color(0xFFE47640)      // Lightened orange from D15C00
)

val lightExtendedColorScheme = ExtendedColorScheme(
    colorScheme = lightColorScheme(
        primary = Color(0xFF6200EE), // A shade of purple.
        onPrimary = Color(0xFFFFFFFF), // Typically white for light backgrounds.
        primaryContainer = Color(0xFFF3E5F5), // Light purple.
        onPrimaryContainer = Color(0xFF6200EE),

        secondary = Color(0xFF03DAC5), // A type of teal.
        onSecondary = Color(0xFF000000), // Black text on light background.
        secondaryContainer = Color(0xFFE0F2F1), // Light teal.
        onSecondaryContainer = Color(0xFF03DAC5),

        tertiary = Color(0xFFE91E63), // A type of pink.
        onTertiary = Color(0xFFFFFFFF),
        tertiaryContainer = Color(0xFFFCE4EC), // Light pink.
        onTertiaryContainer = Color(0xFFE91E63),

        background = Color(0xFFFFFFFF), // White.
        onBackground = Color(0xFF000000), // Black text.

        surface = Color(0xFFF5F5F5), // Off white, for cards and surfaces.
        onSurface = Color(0xFF000000),
        surfaceVariant = Color(0xFFEEEEEE), // A slight variation to the surface color.
        onSurfaceVariant = Color(0xFF000000),

        inverseSurface = Color(0xFF6200EE),
        inverseOnSurface = Color(0xFFFFFFFF),

        error = Color(0xFFB00020), // Standard error color (a kind of red).
        onError = Color(0xFFFFFFFF),
        errorContainer = Color(0xFFFFCDD2), // Light red.
        onErrorContainer = Color(0xFFB00020),

        outline = Color(0xFFAAAAAA), // Medium gray for borders and lines.
        outlineVariant = Color(0xFFBBBBBB), // Slightly lighter gray.

        scrim = Color(0xAAFFFFFF) // Scrim with some transparency, for overlays.
    ),
    checkboxWork = Color(0xFF4A8FD4),
    checkboxPhysical = Color(0xFF6BCD56),
    checkboxSocial = Color(0xFFFF9744)
)


val LocalExtendedColorScheme = staticCompositionLocalOf<ExtendedColorScheme> {
    error("No ExtendedColorScheme provided")
}

@Composable
fun DailyCheckQuestBoardTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val extendedColorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            // For simplicity, let's assume you'll handle dynamic colors later
            if (darkTheme) darkExtendedColorScheme else lightExtendedColorScheme
        }
        darkTheme -> darkExtendedColorScheme
        else -> lightExtendedColorScheme
    }

    // Move the LocalView.current access and other related logic inside this nested composable
    @Composable
    fun UpdateWindowStatusBar() {
        val view = LocalView.current

        if (!view.isInEditMode) {
            SideEffect {
                val window = (view.context as Activity).window
                window.statusBarColor = extendedColorScheme.colorScheme.background.toArgb()

                // If using a dark theme, set the status bar content to light. Otherwise, set it to dark.
                val insetsController = WindowCompat.getInsetsController(window, view)
                insetsController?.isAppearanceLightStatusBars = !darkTheme
            }
        }
    }

    UpdateWindowStatusBar()  // Call the nested composable

    CompositionLocalProvider(LocalExtendedColorScheme provides extendedColorScheme) {
        MaterialTheme(
            colorScheme = extendedColorScheme.colorScheme,
            typography = Typography,
            content = content
        )
    }
}
