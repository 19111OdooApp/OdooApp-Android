package odoo.miem.android.common.uiKitComponents.text

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp

@Composable
fun SubTitleText(
    @StringRes textRes: Int,
    modifier: Modifier = Modifier,
    isLarge: Boolean = false
) = Text(
    text = stringResource(textRes),
    style = if (isLarge) MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp)
        else MaterialTheme.typography.titleSmall,
    modifier = modifier,
    color = MaterialTheme.colorScheme.onPrimaryContainer
)