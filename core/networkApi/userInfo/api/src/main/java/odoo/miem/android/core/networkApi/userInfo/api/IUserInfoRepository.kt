package odoo.miem.android.core.networkApi.userInfo.api

import io.reactivex.rxjava3.core.Single
import odoo.miem.android.core.networkApi.userInfo.api.source.UpdateFavouriteModulesRequest
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
     * @param request: [UpdateFavouriteModulesRequest] contains id of user model at Odoo API
     * and list of favourite modules ids
     *
     * @return Observable<Boolean> - true or false whether updating Odoo database was successful
     */
    fun updateFavouriteModules(request: UpdateFavouriteModulesRequest): Single<Boolean>
}
