package odoo.miem.android.feature.profile.impl.components.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import me.saket.swipe.SwipeAction
import odoo.miem.android.feature.profile.impl.data.DividedListItemAction

@Composable
fun DividedListItemAction.toSwipeAction() = SwipeAction(
    icon = painterResource(iconRes),
    background = background,
    onSwipe = onSwipe,
)