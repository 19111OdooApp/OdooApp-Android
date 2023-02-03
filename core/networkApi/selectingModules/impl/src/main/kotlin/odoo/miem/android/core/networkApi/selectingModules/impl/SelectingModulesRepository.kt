package odoo.miem.android.core.networkApi.selectingModules.impl

import io.reactivex.rxjava3.core.Single
import odoo.miem.android.core.jsonRpcApiFabric.jsonRpcApi
import odoo.miem.android.core.networkApi.selectingModules.api.ISelectingModulesRepository
import odoo.miem.android.core.networkApi.selectingModules.api.source.OdooGroupsResponse
import odoo.miem.android.core.networkApi.selectingModules.api.source.OdooModulesResponse
import odoo.miem.android.core.networkApi.selectingModules.api.source.UserInfoResponse
import odoo.miem.android.core.networkApi.selectingModules.impl.source.IGetBaseInfo
import timber.log.Timber
import javax.inject.Inject

/**
 * [SelectingModulesRepository] - implementation of [ISelectingModulesRepository]
 *
 * @author Egor Danilov
 */
class SelectingModulesRepository @Inject constructor(): ISelectingModulesRepository {

    private val getBaseInfo by jsonRpcApi<IGetBaseInfo>()

    override fun getUserInfo(): Single<UserInfoResponse> {
        Timber.d("getUserInfo()")

        return Single.fromCallable {
            getBaseInfo.getUserInfo()
        }
    }

    override fun getOdooGroups(): Single<OdooGroupsResponse> {
        Timber.d("getOdooGroups")

        return Single.fromCallable {
            getBaseInfo.getOdooGroups()
        }
    }

    override fun getOdooModules(): Single<OdooModulesResponse> {
        Timber.d("getAllModules()")

        return Single.fromCallable {
            getBaseInfo.getOdooModules()
        }
    }
}