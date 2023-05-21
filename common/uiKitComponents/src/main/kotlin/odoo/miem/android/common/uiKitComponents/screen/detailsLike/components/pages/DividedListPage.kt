package odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.charlex.compose.HtmlText
import me.saket.swipe.SwipeableActionsBox
import odoo.miem.android.common.uiKitComponents.icon.ProfileIcon
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.DividedListType
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.utils.toSwipeAction
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.models.DetailsLikeDividedListItem
import odoo.miem.android.common.uiKitComponents.text.TitleText
import odoo.miem.android.core.uiKitTheme.odooOnGray
import odoo.miem.android.core.uiKitTheme.odooPrimary
import odoo.miem.android.core.uiKitTheme.odooPrimaryDark

@Suppress("MagicNumber")
@Composable
internal fun DividedListPage(
    dividedListType: DividedListType,
    onSheetExpand: (onOpen: Boolean) -> Unit = {},
) = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.fillMaxSize()
) {
    val dividerColor = Color(0x21272B33)
    val newDivider: @Composable () -> Unit = { Divider(thickness = 1.dp, color = dividerColor) }
    val swipeThreshold = 10.dp

    TitleText(
        text = dividedListType.topic,
        isLarge = false,
        color = odooPrimary,
        modifier = Modifier.weight(1f)
    )

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .weight(8f)
            .fillMaxWidth()
    ) {
        item { newDivider() }

        itemsIndexed(dividedListType.items) { index, item ->
            SwipeableActionsBox(
                endActions = item.actions.map { it.toSwipeAction() },
                swipeThreshold = swipeThreshold,
                backgroundUntilSwipeThreshold = MaterialTheme.colorScheme.surfaceColorAtElevation(
                    swipeThreshold
                )
            ) {
                ProfileCard(item = item)
            }

            if (index != dividedListType.items.lastIndex) newDivider()
        }

        item { newDivider() }
    }

    Text(
        text = dividedListType.bottomSheetButtonText,
        style = MaterialTheme.typography.headlineSmall.copy(
            textDecoration = TextDecoration.Underline
        ),
        modifier = Modifier
            .padding(top = 16.dp)
            .weight(1f)
            .clickable {
                onSheetExpand(true)
            },
        color = odooPrimary,
    )
}

@Composable
private fun ProfileCard(
    item: DetailsLikeDividedListItem
) = Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
        .padding(vertical = 16.dp)
        .fillMaxWidth()
) {
    val widthPadding = 28.dp

    Spacer(modifier = Modifier.width(widthPadding))

    ProfileIcon(
        avatarUrl = item.avatarUrl,
        userName = item.userName,
        modifier = Modifier.align(Alignment.Top)
    )

    Spacer(modifier = Modifier.width(widthPadding / 2))

    Column(
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = item.date,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 8.sp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = item.topic,
            style = MaterialTheme.typography.bodyMedium,
            color = odooPrimaryDark,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )

        Spacer(modifier = Modifier.height(6.dp))

        HtmlText(
            text = item.description,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
            color = odooOnGray,
            lineHeight = 20.sp,
        )
    }
}
