package odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import me.saket.swipe.SwipeAction
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.models.DividedListItemAction

@Composable
fun DividedListItemAction.toSwipeAction() = SwipeAction(
    icon = painterResource(iconRes),
    background = background,
    onSwipe = onSwipe,
)
