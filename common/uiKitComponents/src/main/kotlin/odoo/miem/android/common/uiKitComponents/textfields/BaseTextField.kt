package odoo.miem.android.common.uiKitComponents.textfields

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import odoo.miem.android.core.uiKitTheme.odooPrimaryGray

/**
 * [BaseTextField] is base implementation of text field
 *
 * @author Vorozhtsov Mikhail
 */
@Composable
fun BaseTextField(
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Done,
    isError: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    @StringRes labelResource: Int? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    onValueChange: (TextFieldValue) -> Unit = {},
    onDone: () -> Unit = {},
    onSearch: (String) -> Unit = {},
    shape: CornerBasedShape = MaterialTheme.shapes.small,
    trailingIcon: @Composable (() -> Unit)? = null,
    value: TextFieldValue,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    _BaseTextField(
        imeAction = imeAction,
        isError = isError,
        keyboardType = keyboardType,
        label = labelResource?.let { stringResource(it) },
        leadingIcon = leadingIcon,
        placeholder = placeholder,
        modifier = modifier,
        readOnly = readOnly,
        enabled = enabled,
        onValueChange = onValueChange,
        onDone = onDone,
        onSearch = onSearch,
        shape = shape,
        trailingIcon = trailingIcon,
        value = value,
        visualTransformation = visualTransformation
    )
}

@Composable
fun BaseTextField(
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Done,
    isError: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    label: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    onValueChange: (TextFieldValue) -> Unit = {},
    onDone: () -> Unit = {},
    onSearch: (String) -> Unit = {},
    shape: CornerBasedShape = MaterialTheme.shapes.small,
    trailingIcon: @Composable (() -> Unit)? = null,
    textStyle: TextStyle? = null,
    value: TextFieldValue,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    _BaseTextField(
        imeAction = imeAction,
        isError = isError,
        keyboardType = keyboardType,
        label = label,
        leadingIcon = leadingIcon,
        placeholder = placeholder,
        modifier = modifier,
        readOnly = readOnly,
        enabled = enabled,
        singleLine = singleLine,
        onValueChange = onValueChange,
        onDone = onDone,
        onSearch = onSearch,
        shape = shape,
        trailingIcon = trailingIcon,
        textStyle = textStyle,
        value = value,
        visualTransformation = visualTransformation
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun _BaseTextField(
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Done,
    isError: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    label: String?,
    leadingIcon: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    onValueChange: (TextFieldValue) -> Unit = {},
    onDone: () -> Unit = {},
    onSearch: (String) -> Unit = {},
    shape: CornerBasedShape = MaterialTheme.shapes.small,
    trailingIcon: @Composable (() -> Unit)? = null,
    textStyle: TextStyle? = null,
    value: TextFieldValue,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle ?: MaterialTheme.typography.bodyMedium,
        enabled = enabled,
        singleLine = singleLine,
        label = label?.let {
            {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        readOnly = readOnly,
        placeholder = placeholder,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onDone = {
                onDone()
                focusManager.clearFocus()
            },
            onSearch = {
                onSearch(value.text)
                focusManager.clearFocus()
            }
        ),
        isError = isError,
        shape = shape,
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
            placeholderColor = odooPrimaryGray,
            disabledBorderColor = odooPrimaryGray,
            disabledTextColor = MaterialTheme.colorScheme.onSurface
        ),
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        modifier = modifier.fillMaxWidth()
    )
}
