package com.oleg1202000.finapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = DarkGrey,
    primaryVariant = Color.Black,
    secondary = Teal200,
    background = Color.Black
)

private val LightColorPalette = lightColors(
    primary = LightGray,
    primaryVariant = Color.White,
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
    val colors: Colors
    val typography: androidx.compose.material.Typography
    if (darkTheme) {
            colors = DarkColorPalette
            typography = Typography
    } else {
            colors = LightColorPalette
            typography = Typography
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}