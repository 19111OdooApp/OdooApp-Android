package odoo.miem.android.common.uiKitComponents.text

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign

/**
 * [DisplayText] is largest text on the screen,
 * reserved for short, important info
 *
 * @param textRes resource on text, which will be displayed
 * @param isLarge true or false, depends on size of subtitle
 *
 * @author Egor Danilov
 */
@Composable
fun DisplayText(
    @StringRes textRes: Int,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    textAlign: TextAlign = TextAlign.Start,
    isLarge: Boolean = true
) = Text(
    text = stringResource(textRes),
    style = if (isLarge) {
        MaterialTheme.typography.displayLarge
    } else {
        MaterialTheme.typography.displayMedium
    },
    modifier = modifier,
    textAlign = textAlign,
    color = color
)
