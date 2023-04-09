package odoo.miem.android.common.uiKitComponents.text

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

/**
 * [TitleText] is text for title
 *
 * @param textRes resource on text, which will be displayed
 * @param isLarge true or false, depends on size of subtitle
 *
 * @author Vorozhtsov Mikhail
 */
@Composable
fun TitleText(
    @StringRes textRes: Int,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    isLarge: Boolean = true
) = BaseTitleText(
    text = stringResource(textRes),
    textAlign = textAlign,
    modifier = modifier,
    isLarge = isLarge,
    color = color
)

/**
 * [TitleText] is text for title
 *
 * @param text string with text, which will be displayed
 * @param isLarge true or false, depends on size of subtitle
 *
 * @author Vorozhtsov Mikhail
 */
@Composable
fun TitleText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    textAlign: TextAlign = TextAlign.Start,
    isLarge: Boolean = true
) = BaseTitleText(
    text = text,
    modifier = modifier,
    isLarge = isLarge,
    color = color
    textAlign = textAlign,
    isLarge = isLarge
)

/**
 * [BaseTitleText] - base realization of TitleText
 *
 * @param text string with text, which will be displayed
 * @param isLarge true or false, depends on size of subtitle
 *
 * @author Vorozhtsov Mikhail
 */
@Composable
private fun BaseTitleText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    textAlign: TextAlign = TextAlign.Start,
    isLarge: Boolean
) = Text(
    text = text,
    textAlign = textAlign,
    style = if (isLarge) MaterialTheme.typography.titleLarge else MaterialTheme.typography.titleMedium,
    modifier = modifier,
    color = color
)
