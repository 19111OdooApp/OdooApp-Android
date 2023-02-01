package odoo.miem.android.core.networkApi.selectingModules.impl

import io.reactivex.rxjava3.core.Single
import odoo.miem.android.core.jsonRpcApiFabric.jsonRpcApi
import odoo.miem.android.core.networkApi.selectingModules.api.ISelectingModulesRepository
import odoo.miem.android.core.networkApi.selectingModules.api.source.OdooGroup
import odoo.miem.android.core.networkApi.selectingModules.api.source.UserInfoResponse
import odoo.miem.android.core.networkApi.selectingModules.impl.source.IGetBaseInfo
import odoo.miem.android.core.utils.network.RequestHelpers
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
            getBaseInfo.userInfo()
        }
    }

    override fun getAllModules(): Single<List<OdooGroup>> {
        Timber.d("getAllModules()")

        return Single.fromCallable {
            getBaseInfo.getModules(
                model = RequestHelpers.allModulesModel,
                fields = RequestHelpers.allModulesFields
            )
        }
    }
}