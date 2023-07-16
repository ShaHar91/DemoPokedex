package be.christiano.demopokedex.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

// Cheat sheet about all the colors and components
// https://medium.com/@daniel.atitienei/material-design-3-in-jetpack-compose-cheat-sheet-b5dd426c10e

private val DarkColorScheme = darkColorScheme(
    primary = HintOfRed,
    onPrimary = EerieBlack,
    primaryContainer = EerieBlack,
//    onPrimaryContainer = EerieBlack,
//    inversePrimary = EerieBlack,
//    secondary = EerieBlack,
//    onSecondary = EerieBlack,
//    secondaryContainer = EerieBlack,
//    onSecondaryContainer = EerieBlack,
//    tertiary = EerieBlack,
//    onTertiary = EerieBlack,
//    tertiaryContainer = EerieBlack,
//    onTertiaryContainer = EerieBlack,
    background = EerieBlack,
//    onBackground = EerieBlack,
    surface = Color(0xFF4c4c4c), // Searchview background
//    onSurface = EerieBlack,
//    surfaceVariant = Color.White.copy(0.2f), // Filled TextView (TextField), Switch, LinearProgressbar (background track), Card
//    onSurfaceVariant = EerieBlack,
//    surfaceTint = EerieBlack,
//    inverseSurface = EerieBlack,
//    inverseOnSurface = EerieBlack,
//    error = EerieBlack,
//    onError = EerieBlack,
//    errorContainer = EerieBlack,
//    onErrorContainer = EerieBlack,
//    outline = EerieBlack,
//    outlineVariant = EerieBlack,
//    scrim = EerieBlack
)

private val LightColorScheme = lightColorScheme(
    primary = EerieBlack,
    onPrimary = HintOfRed,
    primaryContainer = HintOfRed, // floating action button
//    onPrimaryContainer = HintOfRed,
//    inversePrimary = HintOfRed,
//    secondary = HintOfRed,
//    onSecondary = HintOfRed,
//    secondaryContainer = HintOfRed,
//    onSecondaryContainer = HintOfRed,
//    tertiary = HintOfRed,
//    onTertiary = HintOfRed,
//    tertiaryContainer = HintOfRed,
//    onTertiaryContainer = HintOfRed,
    background = HintOfRed,
//    onBackground = HintOfRed,
    surface = Color.White,
//    onSurface = HintOfRed,
//    surfaceVariant = Color.White,
//    onSurfaceVariant = HintOfRed,
//    surfaceTint = HintOfRed,
//    inverseSurface = HintOfRed,
//    inverseOnSurface = HintOfRed,
//    error = HintOfRed,
//    onError = HintOfRed,
//    errorContainer = HintOfRed,
//    onErrorContainer = HintOfRed,
//    outline = HintOfRed,
//    outlineVariant = HintOfRed,
//    scrim = HintOfRed

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun DemoPokedexTheme(
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
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.background.toArgb()
            (view.context as Activity).window.navigationBarColor = colorScheme.background.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = !darkTheme
        }
    }

    CompositionLocalProvider(LocalSpacing provides Spacing()) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}