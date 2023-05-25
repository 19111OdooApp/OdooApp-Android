package odoo.miem.android.common.network.selectingModules.impl.entities

import com.squareup.moshi.Json
import odoo.miem.android.common.network.selectingModules.api.entities.User

internal data class UserWithFavouriteModules(
    val user: User,
    val favouriteModules: List<String>
)

internal data class ImplementedModules(
    @Json(name = "modules") val modules: List<ImplementedModule>
)

internal data class ImplementedModule(
    @Json(name = "nameEn") val nameEn: String,
    @Json(name = "nameRu") val nameRu: String
)
