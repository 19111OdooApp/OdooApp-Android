package odoo.miem.android.common.uiKitComponents.utils

import androidx.compose.ui.graphics.Color
import odoo.miem.android.core.uiKitTheme.cardColors

/**
 * Extension function for generation background color of Big Module card
 *
 * @author Vorozhtsov Mikhail
 */
internal fun String.getBackgroundColorCard(): Color =
    cardColors[(this.firstOrNull()?.code ?: 'A'.code) % cardColors.size].copy(
        alpha = 0.8F
    )

/**
 * Extension function for generation background color of Small Module card
 *
 * @author Vorozhtsov Mikhail
 */
internal fun String.getBackgroundIconColorSmallCard(): Color = this.getBackgroundColorCard().copy(
    alpha = 0.5F
)