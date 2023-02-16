package odoo.miem.android.common.network.selectingModules.api.entities

data class User(
    val modelId: Int,
    val uid: Int,
    val name: String
)

data class OdooModule(
    val id: Int,
    val name: String,
    val parentId: Int?,
    val childModules: MutableList<OdooModule>,
    var numberOfNotifications: Int = 0,
    var isFavourite: Boolean = false,
    val isImplemented: Boolean = false,
)
