package odoo.miem.android.common.uiKitComponents.dividers

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import odoo.miem.android.core.uiKitTheme.dividerCommonHeight
import odoo.miem.android.core.uiKitTheme.dividerCommonWidth

/**
 * [Divider] - divider for dividing visually some elements, e.g. buttons
 * Looks like two horizontal lines with Text between
 *
 * @author Egor Danilov
 */
@Composable
fun Divider(
    textModifier: Modifier,
    paddingModifier: Modifier = Modifier,
    @StringRes textResource: Int
) = Row(
    modifier = paddingModifier
) {
    DivideLine()

    Text(
        text = stringResource(textResource),
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        modifier = textModifier
    )

    DivideLine()
}

@Composable
private fun RowScope.DivideLine() = Surface(
    color = MaterialTheme.colorScheme.onSecondaryContainer,
    modifier = Modifier
        .size(
            width = dividerCommonWidth,
            height = dividerCommonHeight
        )
        .align(Alignment.CenterVertically),
    content = {}
)
