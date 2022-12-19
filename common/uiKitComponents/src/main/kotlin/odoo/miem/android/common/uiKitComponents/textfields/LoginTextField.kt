package odoo.miem.android.common.uiKitComponents.textfields

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import odoo.miem.android.common.uiKitComponents.R
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.odooPrimaryGray

/**
 * [LoginTextField] - Text Field for [AuthorizationScreen]
 *
 * @author Egor Danilov
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoginTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit = {},
    @StringRes labelResource: Int,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    imeAction: ImeAction = ImeAction.Done,
    isError: Boolean = false,
    modifier: Modifier = Modifier
) {
    val trailingIcon = @Composable {
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
    }

    BaseTextField(
        value = value,
        onValueChange = onValueChange,
        labelResource = labelResource,
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
        imeAction = imeAction,
        isError = isError,
        modifier = modifier
    )
}
