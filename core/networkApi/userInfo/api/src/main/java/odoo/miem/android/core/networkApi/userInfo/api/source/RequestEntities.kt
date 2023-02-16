package odoo.miem.android.core.networkApi.userInfo.api.source

data class UpdateFavouriteModulesRequest(
    val body: List<Any> // model of request is [idOfUserModel, {"x_favouriteModules": [favourite modules ids]}]
)