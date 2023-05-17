package odoo.miem.android.common.uiKitComponents.text

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign

/**
 * [LabelText] is text for things like the text inside components
 * or for very small text in the content body, such as captions
 *
 * @param textRes resource on text, which will be displayed
 * @param isLarge true or false, depends on size of subtitle
 *
 * @author Egor Danilov
 */
@Composable
fun LabelText(
    @StringRes textRes: Int,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    isLarge: Boolean = false
) = Text(
    text = stringResource(textRes),
    style = if (isLarge) {
        MaterialTheme.typography.labelLarge
    } else {
        MaterialTheme.typography.labelMedium
    },
    modifier = modifier,
    textAlign = textAlign,
    color = MaterialTheme.colorScheme.onPrimaryContainer
)

@Composable
fun LabelText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    isMedium: Boolean = true,
    isLarge: Boolean = false
) = Text(
    text = text,
    style = if (isLarge) {
        MaterialTheme.typography.labelLarge
    } else if (isMedium) {
        MaterialTheme.typography.labelMedium
    } else {
        MaterialTheme.typography.labelSmall
    },
    modifier = modifier,
    textAlign = textAlign,
    color = MaterialTheme.colorScheme.onPrimaryContainer
)
