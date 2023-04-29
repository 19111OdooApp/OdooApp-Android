package odoo.miem.android.core.networkApi.userModules.impl

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.jsonRpcApiFabric.jsonRpcApi
import odoo.miem.android.core.networkApi.remoteConfig.api.di.IRemoteConfigApi
import odoo.miem.android.core.networkApi.userInfo.api.IUserModulesRepository
import odoo.miem.android.core.networkApi.userInfo.api.source.OdooGroupsResponse
import odoo.miem.android.core.networkApi.userInfo.api.source.OdooModulesResponse
import odoo.miem.android.core.networkApi.userModules.impl.source.IUserModules
import timber.log.Timber
import javax.inject.Inject

/**
 * [UserModulesRepository] - implementation of [IUserModulesRepository]
 *
 * @author Egor Danilov
 */
class UserModulesRepository @Inject constructor() : IUserModulesRepository {

    private val userModules by jsonRpcApi<IUserModules>()
    private val remoteConfig by api(IRemoteConfigApi::remoteConfig)

    override fun getOdooGroups(): Single<OdooGroupsResponse> {
        Timber.d("getOdooGroups()")

        return Single.fromCallable {
            userModules.getOdooGroups()
        }.subscribeOn(Schedulers.io())
    }

    override fun getOdooModules(): Single<OdooModulesResponse> {
        Timber.d("getAllModules()")

        return Single.fromCallable {
            userModules.getOdooModules()
        }.subscribeOn(Schedulers.io())
    }

    override fun fetchImplementedModules(): Single<String> {
        Timber.d("fetchImplementedModules()")
        return remoteConfig.fetchImplementedModules()
    }
}
