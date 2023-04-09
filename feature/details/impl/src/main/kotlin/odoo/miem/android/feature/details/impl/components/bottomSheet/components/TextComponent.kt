package odoo.miem.android.feature.details.impl.components.bottomSheet.components

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import odoo.miem.android.common.uiKitComponents.text.SubtitleText
import odoo.miem.android.common.uiKitComponents.textfields.BaseTextField

@Composable
internal fun TextComponent(
    placeholderText: String,
    isLarge: Boolean = true,
    onDone: (result: String) -> Unit = {}
) {
    var input by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    BaseTextField(
        value = input,
        onValueChange = {
            input = it
            onDone(input.text)
        },
        placeholder = {
            SubtitleText(
                text = placeholderText,
                color = Color.Gray
            )
        },
        label = null,
        singleLine = false,
        textStyle = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Start),
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = if (isLarge) 100.dp else 60.dp)
    )
}
