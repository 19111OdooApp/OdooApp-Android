package odoo.miem.android.feature.profile.impl.components.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import odoo.miem.android.common.uiKitComponents.icon.ProfileIcon
import odoo.miem.android.common.uiKitComponents.text.HeadlineText
import odoo.miem.android.common.uiKitComponents.text.SubtitleText
import odoo.miem.android.common.uiKitComponents.text.TitleText
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.odooGray
import odoo.miem.android.core.uiKitTheme.odooPrimary
import odoo.miem.android.core.uiKitTheme.odooPrimaryDark
import odoo.miem.android.feature.profile.impl.R

data class DetailsListItem(
    val topic: String = "Arina Shoshina",
    val description: String = "Показываю максимальный размер показываемых заметки",

    val avatarUrl: String? = null,
    val userName: String = "Arina Shoshina",

    val date: String = "17 hours ago"
)

@Composable
fun LogNotePage(
    detailsList: List<DetailsListItem> = listOf(
        DetailsListItem(),
        DetailsListItem(),
        DetailsListItem(),
        DetailsListItem(),
        DetailsListItem(),
        DetailsListItem(),
        DetailsListItem(),
        DetailsListItem(),
        DetailsListItem(),
        DetailsListItem(),
    )
) = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.fillMaxSize()
) {

    val editAction = SwipeAction(
        icon = painterResource(R.drawable.ic_edit),
        background = odooGray,
        onSwipe = { },
    )

    val cancelAction = SwipeAction(
        icon = painterResource(R.drawable.ic_cancel),
        background = odooPrimary,
        onSwipe = { },
    )

    val dividerColor = Color(0x21272B33)

    TitleText(
        text = stringResource(R.string.title_log_note),
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

        item {
            Divider(thickness = 1.dp, color = dividerColor)
        }

        itemsIndexed(detailsList) { index, item ->
            SwipeableActionsBox(
                endActions = listOf(editAction, cancelAction),
                swipeThreshold = 10.dp,
                backgroundUntilSwipeThreshold = MaterialTheme.colorScheme.surfaceColorAtElevation(
                    40.dp
                )
            ) {
                ProfileCard(
                    item = item,
                )
            }

            if (index != detailsList.lastIndex)
                Divider(thickness = 1.dp, color = dividerColor)
        }

        item {
            Divider(thickness = 1.dp, color = dividerColor)
        }
    }

    Text(
        text = "Add new log note",
        style = MaterialTheme.typography.headlineSmall.copy(
            textDecoration = TextDecoration.Underline
        ),
        modifier = Modifier
            .padding(top = 16.dp)
            .weight(1f),
        color = odooPrimary,
    )
}


@Composable
private fun ProfileCard(
    item: DetailsListItem
) = Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
        .padding(vertical = 16.dp)
        .fillMaxWidth()
) {
    Spacer(modifier = Modifier.width(28.dp))

    ProfileIcon(
        avatarUrl = item.avatarUrl,
        userName = item.userName
    )

    Spacer(modifier = Modifier.width(14.dp))

    Column {
        Row {
            Text(
                text = item.topic,
                style = MaterialTheme.typography.bodyMedium,
                color = odooPrimaryDark,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = item.date,
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = item.description,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}