package com.giussepr.mubi.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
  primary = Purple,
  secondary = Blue,
  background = Color(0xFF121212),
  surface = Color(0xFF121212),
  onBackground = White,
  onSurface = White,
  error = Red,
)

private val LightColorPalette = lightColors(
  primary = Purple,
  secondary = Blue,
  background = Background,
  surface = Background,
  error = Red,
  onBackground = ColorText,
  onSurface = ColorText
)

@Composable
fun MubiTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
  val colors = if (darkTheme) {
    DarkColorPalette
  } else {
    LightColorPalette
  }

  MaterialTheme(
    colors = colors,
    typography = Typography,
    shapes = Shapes,
    content = content
  )
}
