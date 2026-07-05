package com.example.quizassignment.core.designsystem.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = QuizPrimary,
    onPrimary = White,
    secondary = QuizSuccess,
    onSecondary = White,
    tertiary = QuizGold,
    onTertiary = QuizInk,
    background = QuizHeaderBackground,
    onBackground = QuizInk,
    surface = QuizSheetBackground,
    onSurface = QuizInk,
    surfaceVariant = QuizBlueWash,
    onSurfaceVariant = QuizInk,
    error = QuizCoral,
    onError = White
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF8DB5FF),
    onPrimary = Ink900,
    secondary = Color(0xFF7DE2CB),
    onSecondary = Ink900,
    tertiary = Color(0xFFFFD37A),
    onTertiary = Ink900,
    background = Color(0xFF0B1220),
    onBackground = White,
    surface = Color(0xFF111827),
    onSurface = White,
    surfaceVariant = Color(0xFF1F2937),
    onSurfaceVariant = Color(0xFFD0D5DD),
    error = Color(0xFFF97066),
    onError = Ink900
)

@Composable
fun QuizAssignmentTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColors
        else -> LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
