package odoo.miem.android.common.uiKitComponents.text

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun TitleText(
    @StringRes textRes: Int,
    modifier: Modifier = Modifier,
    isLarge: Boolean = true
) = BaseTitleText(
    text = stringResource(textRes),
    modifier = modifier,
    isLarge = isLarge
)

@Composable
fun TitleText(
    text: String,
    modifier: Modifier = Modifier,
    isLarge: Boolean = true
) = BaseTitleText(
    text = text,
    modifier = modifier,
    isLarge = isLarge
)

@Composable
private fun BaseTitleText(
    text: String,
    modifier: Modifier = Modifier,
    isLarge: Boolean
) = Text(
    text = text,
    style = if (isLarge) MaterialTheme.typography.titleLarge else MaterialTheme.typography.titleMedium,
    modifier = modifier,
    color = MaterialTheme.colorScheme.onSecondaryContainer
)
