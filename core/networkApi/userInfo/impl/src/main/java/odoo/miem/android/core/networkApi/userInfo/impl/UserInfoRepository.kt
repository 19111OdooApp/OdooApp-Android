package odoo.miem.android.core.networkApi.userInfo.impl

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.jsonRpcApiFabric.jsonRpcApi
import odoo.miem.android.core.networkApi.remoteConfig.api.di.IRemoteConfigApi
import odoo.miem.android.core.networkApi.userInfo.api.IUserInfoRepository
import odoo.miem.android.core.networkApi.userInfo.api.source.ImplementedModules
import odoo.miem.android.core.networkApi.userInfo.api.source.UpdateFavouriteModulesRequest
import odoo.miem.android.core.networkApi.userInfo.api.source.UserInfoResponse
import odoo.miem.android.core.networkApi.userInfo.impl.helpers.UserInfoSerializer
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
    private val remoteConfig by api(IRemoteConfigApi::remoteConfig)

    private val serializer = UserInfoSerializer()

    override fun getUserInfo(): Single<UserInfoResponse> {
        Timber.d("getUserInfo()")

        return Single.fromCallable {
            userInfo.getUserInfo()
        }.subscribeOn(Schedulers.io())
    }

    override fun updateFavouriteModules(
        userModelId: Int,
        favouriteModules: List<Int>
    ): Single<Boolean> {
        Timber.d("updateFavouriteModules()")

        val request = UpdateFavouriteModulesRequest(
            args = listOf(
                userModelId,
                mapOf(FAVOURITE_MODULES_KEY to favouriteModules)
            )
        )

        return Single.fromCallable {
            userInfo.updateFavouriteModules(args = request.args)
        }.subscribeOn(Schedulers.io())
    }

    override fun fetchImplementedModules(): List<Int> {
        val json = remoteConfig.fetchImplementedModules()
        val implementedModules = serializer.deserialize(ImplementedModules::class.java, json)?.modules
            ?.map { it.id }
            ?: emptyList()

        Timber.d("fetchImplementedModules(): result = $implementedModules")

        return implementedModules
    }

    override fun deserializeFavouriteModules(jsonString: String): List<Int> {
        return serializer.deserializeList(jsonString) ?: emptyList()
    }

    private companion object {
        const val FAVOURITE_MODULES_KEY = "x_favourite_modules"
    }
}
