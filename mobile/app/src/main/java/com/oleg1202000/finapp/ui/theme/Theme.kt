package com.oleg1202000.finapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = DarkGrey,
    primaryContainer = Color.Black,
    secondary = Teal200,
    background = Color.Black
)

private val LightColorPalette = lightColorScheme(
    primary = LightGray,
    primaryContainer = Color.White,
    secondary = cerulean,
    background = Color.White,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun FinappTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors: ColorScheme
    val typography: androidx.compose.material3.Typography
    if (darkTheme) {
            colors = DarkColorPalette
            typography = Typography
    } else {
            colors = LightColorPalette
            typography = Typography
    }

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}