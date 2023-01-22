package odoo.miem.android.core.uiKitTheme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorPalette = darkColorScheme(
    primary = odooPrimary,
    onPrimary = Color.White,
    secondary = odooDarkBackground,
    primaryContainer = odooPrimaryDark,
    onPrimaryContainer = Color.White,
    onSecondaryContainer = Color.White,
    surfaceVariant = odooPrimary.copy(alpha = 0.1f),
    inverseSurface = Color.White,
    inverseOnSurface = Color.Black,
    tertiary = Color.White,
    onTertiary = Color.White,
    background = odooDarkBackground,
    onBackground = Color.White,
    surface = odooDarkBackground
)

private val LightColorPalette = lightColorScheme(
    primary = odooPrimary,
    onPrimary = Color.Black,
    secondary = odooPrimaryLight,
    primaryContainer = odooPrimaryLight,
    onPrimaryContainer = Color.Black,
    onSecondaryContainer = Color.Black,
    surfaceVariant = odooPrimary.copy(alpha = 0.1f),
    inverseSurface = Color.White,
    inverseOnSurface = Color.Black,
    tertiary = odooPrimaryGray,
    onTertiary = Color.Black,
    background = odooLightGrayBackground,
    onBackground = Color.Black,
    surface = odooLightGrayBackground
)

@Composable
fun OdooMiemAndroidTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    // TODO Adaptation for dynamic colors
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        val currentWindow = (view.context as? Activity)?.window
            ?: throw Exception("Not in an activity - unable to get Window reference")

        SideEffect {
            currentWindow.statusBarColor = colors.background.toArgb()
            WindowCompat.getInsetsController(currentWindow, view).isAppearanceLightStatusBars =
                !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        content = content
    )
}
