package com.theblackbit.rickandmorty.core.resources.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val darkColorScheme = darkColorScheme(
    primary = Aqua80,
    secondary = Green80,
    tertiary = Green40,
    background = DarkMate,
    onBackground = WhiteLight,
    onPrimary = WhiteLight,
    onSecondary = WhiteLight,
    onTertiary = WhiteLight,
    primaryContainer = Green80,
    surface = DarkMate2,
    onSurface = WhiteLight
)

private val lightColorScheme = lightColorScheme(
    primary = Aqua80,
    secondary = Green80,
    tertiary = Green40,
    background = WhiteLight,
    onBackground = DarkMate2,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    primaryContainer = Green80,
    surface = Color.White,
    onSurface = DarkMate2
)

@Composable
fun RMTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.copy(alpha = 0.7f).toArgb()
            window.navigationBarColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Color.Transparent.toArgb()
            } else {
                colorScheme.background.copy(alpha = 0.7f).toArgb()
            }

            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
