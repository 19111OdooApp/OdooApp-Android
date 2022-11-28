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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun LoginTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit = {},
    @StringRes labelResource: Int,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    isError: Boolean = false
) {
    val focusManager = LocalFocusManager.current
    val textStyle = MaterialTheme.typography.bodyMedium

    var isPasswordVisible by remember { mutableStateOf(false) }

    val trailingIcon = @Composable {
        AnimatedVisibility(
            visible = value.text.isNotEmpty(),
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            if (keyboardType == KeyboardType.Password) {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = stringResource(R.string.text_field_trailing_icon_desc),
                        tint = odooPrimaryGray
                    )
                }
            } else {
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
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        singleLine = true,
        label = { Text(text = stringResource(labelResource), style = textStyle) },
        visualTransformation =
        if (isPasswordVisible || visualTransformation == VisualTransformation.None) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        isError = isError,
        shape = MaterialTheme.shapes.small,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colorScheme.onSurface,
            cursorColor = MaterialTheme.colorScheme.primary,
            errorCursorColor = MaterialTheme.colorScheme.error,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = odooPrimaryGray,
            errorBorderColor = MaterialTheme.colorScheme.error,
            errorTrailingIconColor = MaterialTheme.colorScheme.error,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
            errorLabelColor = MaterialTheme.colorScheme.error,
            placeholderColor = odooPrimaryGray
        ),
        trailingIcon = trailingIcon,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = mainHorizontalPadding)
            .padding(top = 20.dp),
    )
}
