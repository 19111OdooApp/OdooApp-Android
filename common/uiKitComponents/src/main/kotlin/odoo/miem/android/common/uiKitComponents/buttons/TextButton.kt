package odoo.miem.android.common.uiKitComponents.buttons

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import odoo.miem.android.core.uiKitTheme.commonPadding

/**
 * [TextButton] - basic Material You filled button
 * Contains Text and optional Icon
 *
 * @author Egor Danilov
 */
@Composable
fun TextButton(
    onClick: () -> Unit = {},
    colors: ButtonColors,
    modifier: Modifier,
    @StringRes textId: Int,
    @DrawableRes iconId: Int? = null,
    isEnabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = isEnabled,
        contentPadding = PaddingValues(vertical = 15.dp),
        colors = colors,
        modifier = modifier
    ) {
        // I know, its stupid but I found some kind of bug
        // If set end padding to Image and remove start padding from Text, icon become very small
        if (iconId != null) {
            Image(
                painter = painterResource(iconId),
                contentDescription = null,
                modifier = Modifier.size(buttonIconSize)
            )

            Text(
                text = stringResource(textId),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = commonPadding)
            )
        }
        else {
            Text(
                text = stringResource(textId),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}