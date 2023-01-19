package odoo.miem.android.core.networkApi.selectingModules.impl

import io.reactivex.rxjava3.core.Single
import odoo.miem.android.core.jsonRpcApiFabric.jsonRpcApi
import odoo.miem.android.core.networkApi.selectingModules.api.ISelectingModulesRepository
import odoo.miem.android.core.networkApi.selectingModules.impl.source.IGetModules
import javax.inject.Inject

/**
 * [SelectingModulesRepository] - implementation of [ISelectingModulesRepository]
 *
 * @author Egor Danilov
 */
class SelectingModulesRepository @Inject constructor(): ISelectingModulesRepository {

    private val getModules by jsonRpcApi<IGetModules>()

    override fun getAllModules(login: String, password: String): Single<Int> {
        TODO("Not yet implemented")
    }
}