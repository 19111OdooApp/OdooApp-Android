package odoo.miem.android.common.network.selectingModules.impl.entities

import com.squareup.moshi.Json

internal data class ImplementedModules(
    @Json(name = "modules") val modules: List<Module>
) {
    data class Module(
        @Json(name = "id") val id: Int,
        @Json(name = "name") val name: String
    )
}