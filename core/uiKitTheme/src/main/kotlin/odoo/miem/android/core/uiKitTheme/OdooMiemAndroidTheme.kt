package odoo.miem.android.core.uiKitTheme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorPalette = darkColorScheme(
    primary = odooPrimary,
    onPrimary = Color.White,
    secondary = odooDarkBackground,
    secondaryContainer = odooDarkBackground.copy(alpha = 0.5f),
    surfaceVariant = odooPrimary.copy(alpha = 0.1f),
    background = odooDarkBackground,
    surface = odooDarkBackground
)

private val LightColorPalette = lightColorScheme(
    primary = odooPrimary,
    secondary = odooDarkBackground,
    secondaryContainer = odooDarkBackground.copy(alpha = 0.5f),
    surfaceVariant = odooPrimary.copy(alpha = 0.1f),
    background = odooLightGrayBackground,
    surface = odooLightGrayBackground
)

@Composable
fun OdooMiemAndroidTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (darkTheme) dynamicDarkColorScheme(LocalContext.current)
        else dynamicLightColorScheme(LocalContext.current)
    } else {
        if (darkTheme) DarkColorPalette
        else LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        content = content
    )
}
