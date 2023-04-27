package odoo.miem.android.common.uiKitComponents.utils

import androidx.compose.ui.graphics.Color
import odoo.miem.android.core.uiKitTheme.cardColors

/**
 * Extension function for generation background color of Big Module card
 *
 * @author Vorozhtsov Mikhail
 */
internal fun String.getBackgroundColorCard(): Color {
    return cardColors[((this.hashCode())  % cardColors.size + cardColors.size) % cardColors.size].copy(
        alpha = 0.8F
    )
}
