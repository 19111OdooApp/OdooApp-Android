package odoo.miem.android.common.uiKitComponents.textfields

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.odooPrimaryGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseTextField(
    imeAction: ImeAction = ImeAction.Done,
    isError: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    @StringRes labelResource: Int,
    leadingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    onValueChange: (TextFieldValue) -> Unit = {},
    shape: CornerBasedShape = MaterialTheme.shapes.small,
    trailingIcon: @Composable (() -> Unit)? = null,
    value: TextFieldValue,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.bodyMedium,
        singleLine = true,
        label = { Text(text = stringResource(labelResource), style = MaterialTheme.typography.bodyMedium) },
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
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
            placeholderColor = odooPrimaryGray
        ),
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = mainHorizontalPadding)
    )
}