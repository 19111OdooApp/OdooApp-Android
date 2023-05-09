package odoo.miem.android.core.networkApi.firebaseDatabase.api.source

data class UserRequest(
    val uid: Int,
    val name: String,
    val favouriteModules: List<String> = emptyList()
)