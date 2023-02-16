package odoo.miem.android.core.networkApi.userInfo.api.source

/**
 * json model of request is [ idOfUserModel, { favouriteModulesField: favouriteModulesList } ]
 *
 * @param idOfUserModel Int
 *
 * @param favouriteModulesField String (name of field that contains favourite modules of user on Odoo API)
 *
 * @param favouriteModulesList List of Ints (contains ids of favourite modules)
 */
data class UpdateFavouriteModulesRequest(
    val args: List<Any>
)