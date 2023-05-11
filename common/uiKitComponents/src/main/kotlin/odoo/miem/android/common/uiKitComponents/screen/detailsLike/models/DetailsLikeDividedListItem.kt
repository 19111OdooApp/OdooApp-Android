package odoo.miem.android.common.uiKitComponents.screen.detailsLike.models

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

interface DetailsLikeDividedListItem {
    val topic: String
    val description: String
    val avatarUrl: String?
    val userName: String
    val date: String // TODO Replace with?
    val actions: List<DividedListItemAction>
}

data class DividedListItemAction(
    @DrawableRes val iconRes: Int,
    val background: Color,
    val onSwipe: () -> Unit
)
