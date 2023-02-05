package odoo.miem.android.core.networkApi.selectingModules.impl

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import odoo.miem.android.core.jsonRpcApiFabric.jsonRpcApi
import odoo.miem.android.core.networkApi.selectingModules.api.ISelectingModulesRepository
import odoo.miem.android.core.networkApi.selectingModules.api.source.OdooGroupsResponse
import odoo.miem.android.core.networkApi.selectingModules.api.source.OdooModulesResponse
import odoo.miem.android.core.networkApi.selectingModules.impl.source.IUserModules
import timber.log.Timber
import javax.inject.Inject

/**
 * [SelectingModulesRepository] - implementation of [ISelectingModulesRepository]
 *
 * @author Egor Danilov
 */
class SelectingModulesRepository @Inject constructor(): ISelectingModulesRepository {

    private val userModules by jsonRpcApi<IUserModules>()

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
}