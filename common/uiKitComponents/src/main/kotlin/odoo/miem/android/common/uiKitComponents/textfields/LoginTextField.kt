package odoo.miem.android.common.uiKitComponents.textfields

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import odoo.miem.android.core.uiKitTheme.odooPrimaryGray
import odoo.miem.android.common.uiKitComponents.R

/**
 * [LoginTextField] - Text Field for [AuthorizationScreen]
 *
 * @author Egor Danilov
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginTextField(
    value: TextFieldValue,
    @StringRes labelId: Int,
    trailingIconId: Int? = null,
    onValueChange: (TextFieldValue) -> Unit,
    onTrailingIconClick: () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false,
    modifier: Modifier
) {
    val focusManager = LocalFocusManager.current
    val textStyle = MaterialTheme.typography.bodyMedium

    val trailingIcon = @Composable {
        if (trailingIconId != null) {
            IconButton(onClick = onTrailingIconClick) {
                Icon(
                    painter = painterResource(trailingIconId),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = odooPrimaryGray
                )
            }
        }
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = textStyle,
        singleLine = true,
        label = { Text(text = stringResource(labelId), style = textStyle) },
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = ImeAction.Done),
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
        trailingIcon = trailingIcon
    )
}