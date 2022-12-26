package odoo.miem.android.common.uiKitComponents.text

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

/**
 * [SubTitleText] is text for labels
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
    isLarge: Boolean = false
) = Text(
    text = stringResource(textRes),
    style = if (isLarge) {
        MaterialTheme.typography.labelLarge
    } else {
        MaterialTheme.typography.labelMedium
    },
    modifier = modifier,
    color = MaterialTheme.colorScheme.onPrimaryContainer
)