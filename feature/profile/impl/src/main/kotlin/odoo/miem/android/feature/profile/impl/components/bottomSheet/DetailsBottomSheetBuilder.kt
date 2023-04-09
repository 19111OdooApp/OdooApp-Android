package odoo.miem.android.feature.profile.impl.components.bottomSheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import odoo.miem.android.common.uiKitComponents.text.SubtitleText
import odoo.miem.android.common.uiKitComponents.text.TitleText
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.odooPrimary
import odoo.miem.android.feature.profile.impl.components.bottomSheet.components.DatePickerComponent
import odoo.miem.android.feature.profile.impl.components.bottomSheet.components.ListComponent
import odoo.miem.android.feature.profile.impl.components.bottomSheet.components.TextComponent

@Composable
internal fun DetailsBottomSheetBuilder(
    topic: String,
    onCancel: () -> Unit = {},
//    onDone: (results: List<>) -> Unit, // TODO?
    elements: List<DetailedBottomSheetComponentType>
) = LazyColumn(
    modifier = Modifier
        .padding(horizontal = mainHorizontalPadding)
        .fillMaxSize()
) {
    item {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        ) {
            SubtitleText(
                text = "Cancel",
                modifier = Modifier.clickable {
                    // TODO
                },
                color = odooPrimary,
            )

            SubtitleText(
                text = "Done",
                modifier = Modifier.clickable {
                    // TODO
                },
                color = odooPrimary,
            )
        }
    }

    item {
        Spacer(modifier = Modifier.height(32.dp))

        TitleText(text = topic)

        Spacer(modifier = Modifier.height(16.dp))
    }

    elements.forEach { element ->
        item {
            Spacer(modifier = Modifier.height(16.dp))

            when (element) {
                is DetailedBottomSheetComponentType.BigTextComponentType -> TextComponent(
                    placeholderText = element.placeholderText
                )
                is DetailedBottomSheetComponentType.SmallTextComponentType -> TextComponent(
                    placeholderText = element.placeholderText,
                    isLarge = false
                )
                is DetailedBottomSheetComponentType.ListComponentType -> ListComponent(
                    placeholderText = element.placeholderText,
                    elements = element.values
                )
                is DetailedBottomSheetComponentType.DatePickerComponentType -> DatePickerComponent(
                    placeholderText = element.placeholderText
                )
            }
        }
    }
}