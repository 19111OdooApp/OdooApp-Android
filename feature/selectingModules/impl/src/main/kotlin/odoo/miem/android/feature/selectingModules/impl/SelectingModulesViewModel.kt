package odoo.miem.android.feature.selectingModules.impl

import odoo.miem.android.common.network.selectingModules.api.di.ISelectingModulesInteractorApi
import odoo.miem.android.common.network.selectingModules.api.entities.OdooModule
import odoo.miem.android.common.network.selectingModules.api.entities.User
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

    val userInfoState: ResultSubject<User> by lazyEmptyResultPublishSubject()
    val modulesState: ResultSubject<List<OdooModule>> by lazyEmptyResultPublishSubject()

    fun getUserInfo() {
        Timber.d("getUserInfo()")

        userInfoState.onLoadingState()
        selectingModulesInteractor
            .getUserInfo()
            .schedule(
                userInfoChannel,
                onSuccess = {
                    Timber.d("getUserInfo(): result = $it")
                    userInfoState.onNext(it)
                },
                onError = Timber::e
            )
    }

    fun getUserModules(userUid: Int) {
        Timber.d("getUserModules(): userUid = $userUid")

        modulesState.onLoadingState()
        selectingModulesInteractor
            .getOdooModules(userUid)
            .schedule(
                userModulesChannel,
                onSuccess = {
                    Timber.d("getUserModules(): result = $it")
                    modulesState.onNext(it)
                },
                onError = Timber::e
            )
    }

    private companion object {
        // Do this if there are multiple Rx chains in a viewModel
        val userInfoChannel = Channel()
        val userModulesChannel = Channel()
    }
}
