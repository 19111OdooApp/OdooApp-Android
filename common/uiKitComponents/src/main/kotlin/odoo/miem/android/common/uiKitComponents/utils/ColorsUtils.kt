package odoo.miem.android.common.uiKitComponents.utils

import androidx.compose.ui.graphics.Color
import odoo.miem.android.core.uiKitTheme.cardColors

// TODO Description

internal fun String.getBackgroundColorCard(): Color =
    cardColors[(this.firstOrNull()?.code ?: 'A'.code) % cardColors.size]