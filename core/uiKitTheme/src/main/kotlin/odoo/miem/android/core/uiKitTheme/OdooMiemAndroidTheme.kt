package odoo.miem.android.core.uiKitTheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

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
    tertiary = odooPrimaryGray,
    onTertiary = Color.White,
    background = odooDarkBackground,
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

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        content = content
    )
}
