package odoo.miem.android.core.networkApi.userInfo.impl

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import odoo.miem.android.core.jsonRpcApiFabric.jsonRpcApi
import odoo.miem.android.core.networkApi.userInfo.api.IUserInfoRepository
import odoo.miem.android.core.networkApi.userInfo.api.source.UpdateFavouriteModulesRequest
import odoo.miem.android.core.networkApi.userInfo.api.source.UserInfoResponse
import odoo.miem.android.core.networkApi.userInfo.impl.source.IUserInfo
import timber.log.Timber
import javax.inject.Inject

/**
 * [UserInfoRepository] - implementation of [IUserInfoRepository]
 *
 * @author Egor Danilov
 */
class UserInfoRepository @Inject constructor() : IUserInfoRepository {

    private val userInfo by jsonRpcApi<IUserInfo>()

    override fun getUserInfo(): Single<UserInfoResponse> {
        Timber.d("getUserInfo()")

        return Single.fromCallable {
            userInfo.getUserInfo()
        }.subscribeOn(Schedulers.io())
    }

    override fun updateFavouriteModules(
        request: UpdateFavouriteModulesRequest
    ): Single<Boolean> {
        Timber.d("updateFavouriteModules()")

        return Single.fromCallable {
            userInfo.updateFavouriteModules(args = request)
        }.subscribeOn(Schedulers.io())
    }
}
