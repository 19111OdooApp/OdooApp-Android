package odoo.miem.android.feature.profile.impl.components.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import odoo.miem.android.common.uiKitComponents.icon.ProfileIcon
import odoo.miem.android.common.uiKitComponents.text.TitleText
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.odooGray
import odoo.miem.android.core.uiKitTheme.odooPrimary
import odoo.miem.android.core.uiKitTheme.odooPrimaryDark
import odoo.miem.android.feature.profile.impl.R

data class DetailsListItem(
    val topic: String = "Arina Shoshina",

    val avatarUrl: String = "Arina",
    val userName: String = "A",

    val description: String = "Показываю максимальный размер показываемых заметки",
    val date: String = "17 hours ago"
)

@Composable
fun LogNotePage(
    detailsList: List<DetailsListItem> = listOf(
        DetailsListItem(),
        DetailsListItem(),
    )
) = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .fillMaxSize()
) {

    TitleText(
        text = stringResource(R.string.title_log_note),
        isLarge = false,
        color = odooPrimary,
    )

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


    Spacer(modifier = Modifier.height(20.dp))

    Divider(thickness = 1.dp, color = odooGray)

    detailsList.forEachIndexed { index, item ->
        SwipeableActionsBox(
            endActions = listOf(editAction, cancelAction),
            swipeThreshold = 10.dp,
            backgroundUntilSwipeThreshold = MaterialTheme.colorScheme.surfaceColorAtElevation(40.dp)
        ) {
            ProfileCard(
                item = item,
                isLastItem = index == detailsList.lastIndex
            )
        }
    }

    Divider(thickness = 1.dp, color = odooGray)
}

@Composable
private fun ProfileCard(
    item: DetailsListItem,
    isLastItem: Boolean
) = Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
        .padding(top = 16.dp, start = mainHorizontalPadding)
        .fillMaxWidth()
) {
    ProfileIcon(avatarUrl = item.avatarUrl, userName = item.userName, iconSize = 40.dp)

    Spacer(modifier = Modifier.width(6.dp))

    Column{
        Row {
            Text(
                text = "Arina Shoshina",
                style = MaterialTheme.typography.bodyMedium,
                color = odooPrimaryDark,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "17 hours ago",
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Показываю максимальный размер показываемых замет",
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.bodySmall,
        )

        Spacer(modifier = Modifier.height(16.dp))
        if (!isLastItem) {
            Divider(thickness = 1.dp, color = odooGray)
        }
    }
}