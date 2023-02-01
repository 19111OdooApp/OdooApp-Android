package odoo.miem.android.common.network.selectingModules.impl

import odoo.miem.android.common.network.selectingModules.api.ISelectingModulesInteractor
import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.networkApi.selectingModules.api.di.ISelectingModulesRepositoryApi
import odoo.miem.android.core.utils.state.ErrorResult
import odoo.miem.android.core.utils.state.ResultSingle
import odoo.miem.android.core.utils.state.SuccessResult
import odoo.miem.android.core.utils.state.Result
import timber.log.Timber
import javax.inject.Inject

/**
 * [SelectingModulesInteractor] - implementation of [ISelectingModulesInteractor]
 *
 * @author Egor Danilov
 */
class SelectingModulesInteractor @Inject constructor(): ISelectingModulesInteractor {

    private val selectingModulesRepository by api(ISelectingModulesRepositoryApi::selectingModulesRepository)
    private val dataStore by api(IDataStoreApi::dataStore)

    override fun getUserInfo(): ResultSingle<Unit> {
        Timber.d("getUserInfo()")

        return selectingModulesRepository.getUserInfo()
            .map<Result<Unit>> { info ->
                val userInfo = info.result.records[0]

                Timber.d("getUserInfo(): id = ${userInfo.id}")
                dataStore.setUID(userInfo.id)

                SuccessResult()
            }
            .onErrorReturn {
                Timber.e("getUserInfo(): error message = ${it.message}")
                ErrorResult(R.string.selecting_modules_error)
            }
    }
}