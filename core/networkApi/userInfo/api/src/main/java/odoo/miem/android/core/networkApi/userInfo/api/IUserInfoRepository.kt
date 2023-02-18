package odoo.miem.android.core.networkApi.userInfo.api

import io.reactivex.rxjava3.core.Single
import odoo.miem.android.core.networkApi.userInfo.api.source.UserInfoResponse

/**
 * [IUserInfoRepository] - interface for wrapping data layer
 * logic, which is connected with user info
 *
 * @author Egor Danilov
 */
interface IUserInfoRepository {

    /**
     * [getUserInfo] - function for requesting info about current user

     * @return Observable<[UserInfoResponse]> which provides modules accessible for user
     */
    fun getUserInfo(): Single<UserInfoResponse>

    /**
     * [updateFavouriteModules] - function for sending favourite modules to Odoo API
     *
     * @return Observable<Boolean> - true or false whether updating Odoo database was successful
     */
    fun updateFavouriteModules(userModelId: Int, favouriteModules: List<Int>): Single<Boolean>

    /**
     * [fetchImplementedModules] - function for getting implemented modules from Firebase
     *
     * @return List<Int> with ids of implemented modules
     */
    fun fetchImplementedModules(): List<Int>

    /**
     * [deserializeFavouriteModules] - function for deserializing json with favourite modules which
     * looks like "[1, 2, 3]" (blame Odoo backend)
     *
     * @return List<Int> with ids of favourite modules
     */
    fun deserializeFavouriteModules(jsonString: String): List<Int>
}
