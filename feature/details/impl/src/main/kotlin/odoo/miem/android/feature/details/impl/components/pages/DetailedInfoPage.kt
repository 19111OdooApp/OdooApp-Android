package odoo.miem.android.feature.details.impl.components.pages

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import odoo.miem.android.common.uiKitComponents.bar.RatingBar
import odoo.miem.android.common.uiKitComponents.text.TitleText
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.odooPrimary
import odoo.miem.android.feature.details.impl.R
import odoo.miem.android.feature.details.impl.components.DetailedInfoType
import java.text.SimpleDateFormat

@Composable
internal fun DetailedInfoPage(
    detailedInfoType: DetailedInfoType
) = LazyColumn(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .padding(horizontal = mainHorizontalPadding)
        .fillMaxSize()
) {
    val heightPadding = 16.dp
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")

    detailedInfoType.blocks.forEach { (key, value) ->
        item {
            TitleText(
                text = key,
                isLarge = false,
                color = odooPrimary
            )

            Spacer(modifier = Modifier.height(heightPadding))

            value.forEach { type ->
                when (type) {
                    is DetailedInfoType.TextType -> {
                        textJobPageItem(
                            key = type.key,
                            value = type.text
                        )
                    }
                    is DetailedInfoType.NumberType -> {
                        textJobPageItem(
                            key = type.key,
                            value = stringResource(R.string.item_salary).format(type.number)
                        )
                    }
                    is DetailedInfoType.RatingType -> {
                        baseJobPageItem(type.key) { modifier ->
                            RatingBar(
                                modifier = modifier,
                                rating = type.rating,
                                starsCount = type.starsCount
                            )
                        }
                    }
                    is DetailedInfoType.DateType -> {
                        textJobPageItem(
                            key = type.key,
                            value = dateFormat.format(type.date)
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(heightPadding))
        }
    }
}

@Composable
private fun LazyItemScope.textJobPageItem(
    key: String,
    value: String
) {
    baseJobPageItem(key) { modifier ->
        JobPageItemText(
            text = value,
            modifier = modifier
        )
    }
}

@Suppress("MagicNumber")
@Composable
private fun LazyItemScope.baseJobPageItem(
    key: String,
    valueContent: @Composable (modifier: Modifier) -> Unit
) = Row(
    modifier = Modifier.height(32.dp),
    verticalAlignment = Alignment.CenterVertically,
) {
    JobPageItemText(
        text = key,
        modifier = Modifier.weight(0.4F)
    )

    Divider(
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier
            .fillMaxHeight()
            .width(1.dp)
    )

    valueContent(
        Modifier
            .weight(0.6F)
            .padding(start = 24.dp)
    )
}

@Composable
private fun JobPageItemText(
    text: String,
    modifier: Modifier
) = Text(
    text = text,
    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
    color = MaterialTheme.colorScheme.onPrimary,
    overflow = TextOverflow.Ellipsis,
    maxLines = 1,
    modifier = modifier
)
