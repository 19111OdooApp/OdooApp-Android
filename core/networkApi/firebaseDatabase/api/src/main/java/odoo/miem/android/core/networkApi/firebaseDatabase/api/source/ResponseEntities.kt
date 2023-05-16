package odoo.miem.android.core.networkApi.firebaseDatabase.api.source

data class ModuleIconResponse(
    val moduleNameEn: String?,
    val moduleNameRu: String?,
    val downloadUrl: String?
)

data class StatusIconResponse(
    val statusName: String?,
    val downloadUrl: String?
)

data class FavouriteModulesResponse(
    val modules: List<String>
)
