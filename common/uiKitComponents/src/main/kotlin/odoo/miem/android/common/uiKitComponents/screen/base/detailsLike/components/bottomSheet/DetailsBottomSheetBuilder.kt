package odoo.miem.android.common.uiKitComponents.screen.base.detailsLike.components.bottomSheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import odoo.miem.android.common.uiKitComponents.screen.base.detailsLike.components.bottomSheet.components.DatePickerComponent
import odoo.miem.android.common.uiKitComponents.screen.base.detailsLike.components.bottomSheet.components.ListComponent
import odoo.miem.android.common.uiKitComponents.screen.base.detailsLike.components.bottomSheet.components.TextComponent
import odoo.miem.android.common.uiKitComponents.screen.base.detailsLike.components.bottomSheet.types.DetailedBottomSheetComponentType
import odoo.miem.android.common.uiKitComponents.text.TitleText
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.odooPrimary

@Composable
internal fun DetailsBottomSheetBuilder(
    topic: String,
    onCancel: () -> Unit = {},
    onDone: (results: List<DetailedBottomSheetComponentType>) -> Unit,
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
            Text(
                text = "Cancel",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.clickable {
                    onCancel()
                },
                color = odooPrimary,
            )

            Text(
                text = "Done",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.clickable {
                    onDone(elements)
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
                    placeholderText = element.placeholderText,
                    onDone = { element.result = it }
                )
                is DetailedBottomSheetComponentType.SmallTextComponentType -> TextComponent(
                    placeholderText = element.placeholderText,
                    isLarge = false,
                    onDone = { element.result = it }
                )
                is DetailedBottomSheetComponentType.ListComponentType -> ListComponent(
                    placeholderText = element.placeholderText,
                    elements = element.values,
                    onDone = { element.result = it }
                )
                is DetailedBottomSheetComponentType.DatePickerComponentType -> DatePickerComponent(
                    placeholderText = element.placeholderText,
                    onDone = { element.result = it }
                )
            }
        }
    }
}
