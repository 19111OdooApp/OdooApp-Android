package odoo.miem.android.common.uiKitComponents.textfields

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import odoo.miem.android.common.uiKitComponents.R
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.odooPrimaryGray

/**
 * [SearchTextField] - text field for search in selecting modules screen
 *
 * @author Vorozhtsov Mikhail, Egor Danilov
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SearchTextField(
    value: TextFieldValue,
    modifier: Modifier = Modifier,
    onValueChange: (TextFieldValue) -> Unit = {},
    enabled: Boolean = true,
    @StringRes stringRes: Int = R.string.search_text_field_label
) = BaseTextField(
    value = value,
    onValueChange = onValueChange,
    labelResource = stringRes,
    enabled = enabled,
    leadingIcon = {
        Icon(
            painter = painterResource(R.drawable.ic_search),
            contentDescription = stringResource(R.string.search_text_field_trailing_icon),
            modifier = Modifier.size(20.dp),
            tint = odooPrimaryGray
        )
    },
    trailingIcon = {
        AnimatedVisibility(
            visible = value.text.isNotEmpty(),
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            IconButton(onClick = { onValueChange(TextFieldValue()) }) {
                Icon(
                    painter = painterResource(R.drawable.ic_trailing_icon),
                    contentDescription = stringResource(R.string.text_field_trailing_icon_desc),
                    modifier = Modifier.size(20.dp),
                    tint = odooPrimaryGray
                )
            }
        }
    },
    shape = RoundedCornerShape(16.dp),
    modifier = modifier.padding(horizontal = mainHorizontalPadding)
)
