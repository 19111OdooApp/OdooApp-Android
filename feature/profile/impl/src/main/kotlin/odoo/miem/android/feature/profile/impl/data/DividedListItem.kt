package odoo.miem.android.feature.profile.impl.data

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import odoo.miem.android.core.uiKitTheme.odooGray
import odoo.miem.android.core.uiKitTheme.odooPrimary
import odoo.miem.android.feature.profile.impl.R

// TODO Move to domain
// TODO Delete mocks
data class DividedListItem(
    val topic: String = "Arina Shoshina",
    val description: String = "Показываю максимальный размер показываемых заметки",

    val avatarUrl: String? = null,
    val userName: String = "Arina Shoshina",

    val date: String = "17 hours ago",

    val actions: List<DividedListItemAction> = listOf(
        DividedListItemAction(
            iconRes = R.drawable.ic_edit,
            background = odooGray,
            onSwipe = { }
        ),
        DividedListItemAction(
            iconRes = R.drawable.ic_cancel,
            background = odooPrimary,
            onSwipe = { }
        ),
    )
)

data class DividedListItemAction(
    @DrawableRes val iconRes: Int,
    val background: Color,
    val onSwipe: () -> Unit
)