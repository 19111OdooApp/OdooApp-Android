package odoo.miem.android.common.uiKitComponents.text

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

/**
 * [SubtitleText] is text for subtitle
 *
 * @param textRes resource on text, which will be displayed
 * @param isLarge true or false, depends on size of subtitle
 *
 * @author Vorozhtsov Mikhail
 */
@Composable
fun SubtitleText(
    @StringRes textRes: Int,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    textAlign: TextAlign = TextAlign.Start,
    isLarge: Boolean = false
) = BaseSubtitleText(
    text = stringResource(textRes),
    modifier = modifier,
    color = color,
    textAlign = textAlign,
    isLarge = isLarge
)

/**
 * [SubtitleText] is text for title
 *
 * @param text string with text, which will be displayed
 * @param isLarge true or false, depends on size of subtitle
 *
 * @author Egor Danilov
 */
@Composable
fun SubtitleText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    textAlign: TextAlign = TextAlign.Start,
    isLarge: Boolean = false
) = BaseSubtitleText(
    text = text,
    modifier = modifier,
    color = color,
    textAlign = textAlign,
    isLarge = isLarge
)

/**
 * [BaseSubtitleText] - base realization of [SubtitleText]
 *
 * @param text string with text, which will be displayed
 * @param isLarge true or false, depends on size of subtitle
 *
 * @author Egor Danilov
 */
@Composable
private fun BaseSubtitleText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    textAlign: TextAlign = TextAlign.Start,
    isLarge: Boolean = false
) = Text(
    text = text,
    style = if (isLarge) {
        MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp)
    } else {
        MaterialTheme.typography.titleSmall
    },
    modifier = modifier,
    color = color,
    textAlign = textAlign
)
