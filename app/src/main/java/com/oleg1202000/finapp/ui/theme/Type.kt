package com.oleg1202000.finapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with

val lightColor = Color.Black
val darkColor = Color.White

val Typography = Typography(

    titleLarge = TextStyle(  // h1
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        color = lightColor,
        fontSize = 20.sp
    ),

    bodyMedium = TextStyle(  // body1
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        color = lightColor,
        fontSize = 18.sp
    ),

    labelMedium = TextStyle(  // button
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        color = lightColor,
        fontSize = 14.sp
    ),

    labelSmall = TextStyle( // caption
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        color = lightColor,
        fontSize = 12.sp
    )
)
