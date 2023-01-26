package odoo.miem.android.core.networkApi.selectingModules.api.source

data class OdooGroup(
    val id: Int,
    val name: String,
    val users: List<Int>
)

data class OdooModule(
    val name: String,
    val numberOfNotifications: Int,
    var isLiked: Boolean = false
)
