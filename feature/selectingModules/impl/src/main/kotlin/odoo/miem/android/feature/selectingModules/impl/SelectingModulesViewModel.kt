package odoo.miem.android.feature.selectingModules.impl

import odoo.miem.android.common.network.selectingModules.api.di.ISelectingModulesInteractorApi
import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.di.impl.apiBlocking
import odoo.miem.android.core.platform.presentation.BaseViewModel
import odoo.miem.android.core.utils.di.RxApi
import odoo.miem.android.core.utils.rx.PresentationSchedulers
import odoo.miem.android.core.utils.rx.lazyEmptyResultPublishSubject
import odoo.miem.android.core.utils.rx.onLoadingState
import odoo.miem.android.core.utils.state.ResultSubject
import timber.log.Timber

/**
 * [SelectingModulesViewModel] handle major logic for [SelectingModulesScreen]
 *
 * @author Vorozhtsov Mikhail
 */
class SelectingModulesViewModel(
    schedulers: PresentationSchedulers = apiBlocking(RxApi::presentationSchedulers)
) : BaseViewModel(schedulers) {

    private val selectingModulesInteractor by api(ISelectingModulesInteractorApi::selectingModulesInteractor)
    private val dataStore by api(IDataStoreApi::dataStore)

    val selectingModulesState: ResultSubject<Unit> by lazyEmptyResultPublishSubject()

    fun getUserInfo() {
        Timber.d("getUserInfo")

        selectingModulesState.onLoadingState()
        selectingModulesInteractor
            .getUserInfo()
            .schedule(
                selectingModulesChannel,
                onSuccess = {
                    Timber.d("getUserInfo(): result = $it")
                    selectingModulesState.onNext(it)
                },
                onError = Timber::e
            )
    }

    private companion object {
        // Do this if there are multiple Rx chains in a viewModel
        val selectingModulesChannel = Channel()
    }
}
