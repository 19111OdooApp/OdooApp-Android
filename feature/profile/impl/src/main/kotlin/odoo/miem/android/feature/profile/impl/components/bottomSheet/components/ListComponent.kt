package odoo.miem.android.feature.profile.impl.components.bottomSheet.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import odoo.miem.android.common.uiKitComponents.text.SubtitleText
import odoo.miem.android.common.uiKitComponents.textfields.BaseTextField
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun ListComponent(
    placeholderText: String,
    elements: List<String>,
    onDone: (result: String) -> Unit = {}
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    var currentValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(placeholderText))
    }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = {
            isExpanded = it
        },
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
    ) {

        BaseTextField(
            value = currentValue,
            onValueChange = { currentValue = it },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = isExpanded,
                    onIconClick = { isExpanded = !isExpanded }
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier.exposedDropdownSize()
        ) {
            elements.forEach {
                DropdownMenuItem(
                    onClick = {
                        currentValue = TextFieldValue(it)
                        onDone(it)
                        isExpanded = false
                    }
                ) {
                    SubtitleText(text = it)
                }
            }
        }
    }
}