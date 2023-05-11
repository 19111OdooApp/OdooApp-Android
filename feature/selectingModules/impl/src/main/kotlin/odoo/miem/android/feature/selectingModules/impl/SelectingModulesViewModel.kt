package odoo.miem.android.feature.selectingModules.impl

import androidx.compose.runtime.mutableStateListOf
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
import odoo.miem.android.core.utils.state.ErrorResult
import odoo.miem.android.core.utils.state.ResultSubject
import odoo.miem.android.core.utils.state.SuccessResult
import timber.log.Timber

/**
 * [SelectingModulesViewModel] handle major logic for [SelectingModulesScreen]
 *
 * @author Egor Danilov
 */
class SelectingModulesViewModel(
    schedulers: PresentationSchedulers = apiBlocking(RxApi::presentationSchedulers)
) : BaseViewModel(schedulers) {

    private val selectingModulesInteractor by api(ISelectingModulesInteractorApi::selectingModulesInteractor)

    val userInfoState: ResultSubject<User> by lazyEmptyResultPublishSubject()
    val modulesState: ResultSubject<List<OdooModule>> by lazyEmptyResultPublishSubject()

    private var currentUser: User? = null
    val allModules = mutableStateListOf<OdooModule>()

    private var isReloaded: Boolean = false

    fun getUserInfo() {
        Timber.d("getUserInfo()")

        userInfoState.onLoadingState()
        modulesState.onLoadingState()

        selectingModulesInteractor
            .getUserInfo()
            .schedule(
                userInfoChannel,
                onSuccess = { result ->
                    Timber.d("getUserInfo(): result = $result")

                    result.data?.let { currentUser = it }
                    userInfoState.onNext(result)

                    if (result is SuccessResult) {
                        result.data?.uid?.let {
                            getUserModules(it)
                        }
                    } else {
                        modulesState.onNext(ErrorResult())
                    }
                },
                onError = Timber::e
            )
    }

    fun getUserModules(userUid: Int) {
        Timber.d("getUserModules(): userUid = $userUid")

        if (isReloaded) {
            modulesState.onNext(SuccessResult(allModules))
        } else {
            selectingModulesInteractor
                .getOdooModules(userUid)
                .schedule(
                    userModulesChannel,
                    onSuccess = { result ->
                        Timber.d("getUserModules(): result = $result")

                        result.data?.let { list ->
                            allModules.addAll(list)
                            isReloaded = true
                        }

                        modulesState.onNext(result)
                    },
                    onError = Timber::e
                )
        }
    }

    fun onModuleLikeClick(module: OdooModule) {
        val index = allModules.indexOf(module)
        val previousState = allModules[index].isFavourite

        allModules[index] = allModules[index].copy(isFavourite = !previousState)

        updateUserFavouriteModules(
            favouriteModules = allModules
                .filter { it.isFavourite }
                .map { it.name }
        )
    }

    private fun updateUserFavouriteModules(favouriteModules: List<String>) {
        Timber.d("updateFavouriteModules(): favouriteModules = $favouriteModules")

        currentUser?.let { user ->
            selectingModulesInteractor
                .updateFavouriteModules(
                    user = user,
                    favouriteModules = favouriteModules
                )
                .schedule(
                    userFavouriteModulesChannel,
                    onSuccess = {
                        Timber.d("updateFavouriteModules: result = $it")
                    },
                    onError = Timber::e
                )
        }
    }

    private companion object {
        // Do this if there are multiple Rx chains in a viewModel
        val userInfoChannel = Channel()
        val userModulesChannel = Channel()
        val userFavouriteModulesChannel = Channel()
    }
}
