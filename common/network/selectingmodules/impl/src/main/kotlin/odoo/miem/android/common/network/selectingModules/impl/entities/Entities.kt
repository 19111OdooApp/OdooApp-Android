package odoo.miem.android.common.network.selectingModules.impl.entities

import odoo.miem.android.common.network.selectingModules.api.entities.User

internal data class UserWithFavouriteModules(
    val user: User,
    val favouriteModules: List<Int>
)
