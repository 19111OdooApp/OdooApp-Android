package odoo.miem.android.common.uiKitComponents.buttons

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import odoo.miem.android.core.uiKitTheme.buttonIconSize
import odoo.miem.android.core.uiKitTheme.commonPadding

/**
 * [FilledTextButton] - basic Material You filled button
 * Contains Text and optional Icon
 *
 * @author Egor Danilov
 */
@Composable
fun FilledTextButton(
    onClick: () -> Unit = {},
    colors: ButtonColors,
    modifier: Modifier,
    @StringRes textResource: Int,
    @DrawableRes iconResource: Int? = null,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    isEnabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        enabled = isEnabled,
        contentPadding = PaddingValues(vertical = 15.dp),
        colors = colors,
        modifier = modifier
    ) {
        iconResource?.let {
            Icon(
                painter = painterResource(iconResource),
                contentDescription = stringResource(
                    odoo.miem.android.common.uiKitComponents.R.string.button_icon_desc
                ),
                modifier = Modifier.size(buttonIconSize)
            )
        }

        Text(
            text = stringResource(textResource),
            style = textStyle,
            modifier = Modifier.iconPadding(iconResource)
        )
    }
}

fun Modifier.iconPadding(iconResource: Int?): Modifier =
    if (iconResource != null) then(this.padding(start = commonPadding)) else this
