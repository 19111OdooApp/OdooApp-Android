package odoo.miem.android.common.uiKitComponents.textfields

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import odoo.miem.android.common.uiKitComponents.R
import odoo.miem.android.core.uiKitTheme.odooPrimaryGray

// TODO Description
@Composable
fun SearchTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit = {},
    modifier: Modifier = Modifier
) = BaseTextField(
    value = value,
    onValueChange = onValueChange,
    labelResource = R.string.search_text_field_label,
    leadingIcon = {
        Icon(
            painter = painterResource(R.drawable.ic_search),
            contentDescription = stringResource(R.string.search_text_field_trailing_icon),
            modifier = Modifier.size(20.dp),
            tint = odooPrimaryGray
        )
    },
    shape = RoundedCornerShape(16.dp),
    modifier = modifier
)