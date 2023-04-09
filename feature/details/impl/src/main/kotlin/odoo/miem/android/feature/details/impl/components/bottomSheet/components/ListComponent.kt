package odoo.miem.android.feature.details.impl.components.bottomSheet.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import odoo.miem.android.common.uiKitComponents.text.SubtitleText
import odoo.miem.android.common.uiKitComponents.textfields.BaseTextField

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
