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
 * [HeadlineText] is best-suited for short, high-emphasis text
 *
 * @param textRes - resource on text, which will be displayed
 * @param isLarge - true or false, depends on size of headline
 *
 * @author Egor Danilov
 */
@Composable
fun HeadlineText(
    @StringRes textRes: Int,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    textAlign: TextAlign = TextAlign.Start,
    isLarge: Boolean = true
) = BaseHeadlineText(
    text = stringResource(textRes),
    modifier = modifier,
    color = color,
    textAlign = textAlign,
    isLarge = isLarge
)

/**
 * [HeadlineText] is best-suited for short, high-emphasis text
 *
 * @param text - text, which will be displayed
 * @param isLarge - true or false, depends on size of headline
 *
 * @author Egor Danilov
 */
@Composable
fun HeadlineText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    textAlign: TextAlign = TextAlign.Start,
    isLarge: Boolean = true
) = BaseHeadlineText(
    text = text,
    modifier = modifier,
    color = color,
    textAlign = textAlign,
    isLarge = isLarge
)

/**
 * [HeadlineText] base realization of [HeadlineText]
 *
 * @param text - string with text, which will be displayed
 * @param isLarge - true or false, depends on size of headline
 *
 * @author Egor Danilov
 */
@Composable
private fun BaseHeadlineText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    textAlign: TextAlign = TextAlign.Start,
    isLarge: Boolean = true
) = Text(
    text = text,
    style = if (isLarge) {
        MaterialTheme.typography.headlineMedium
    } else {
        MaterialTheme.typography.headlineSmall
    },
    modifier = modifier,
    textAlign = textAlign,
    color = color
)
