package odoo.miem.android.common.uiKitComponents.utils

import odoo.miem.android.common.uiKitComponents.R

private val statusIconsRes = listOf(
    R.drawable.status_1,
    R.drawable.status_2,
    R.drawable.status_3,
    R.drawable.status_4,
    R.drawable.status_5,
    R.drawable.status_6,
    R.drawable.status_7,
    R.drawable.status_8,
    R.drawable.status_9,
    R.drawable.status_10,
    R.drawable.status_11,
    R.drawable.status_12,
)

fun getStatusIcon(index: Int) = statusIconsRes.getOrNull(index) ?: R.drawable.add_plus
