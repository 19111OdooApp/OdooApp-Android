package odoo.miem.android.common.uiKitComponents.text

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun SubTitleText(
    @StringRes textRes: Int,
    modifier: Modifier = Modifier
) = Text(
    text = stringResource(textRes),
    style = MaterialTheme.typography.titleSmall,
    modifier = modifier,
    color = MaterialTheme.colorScheme.onPrimaryContainer
)