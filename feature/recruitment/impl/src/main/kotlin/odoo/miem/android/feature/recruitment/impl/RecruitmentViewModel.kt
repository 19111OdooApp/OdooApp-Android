package odoo.miem.android.feature.recruitment.impl

import odoo.miem.android.common.network.recruitment.api.di.IRecruitmentInteractorApi
import odoo.miem.android.common.network.recruitment.api.entities.Employee
import odoo.miem.android.common.network.recruitment.api.entities.Status
import odoo.miem.android.common.network.selectingModules.api.di.ISelectingModulesInteractorApi
import odoo.miem.android.common.network.selectingModules.api.entities.User
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.platform.presentation.BaseViewModel
import odoo.miem.android.core.utils.rx.emptyResultBehaviorSubject
import odoo.miem.android.core.utils.rx.lazyEmptyResultPublishSubject
import odoo.miem.android.core.utils.rx.onLoadingState
import odoo.miem.android.core.utils.state.ResultSubject
import odoo.miem.android.core.utils.state.StateResultSubject
import timber.log.Timber

internal class RecruitmentViewModel : BaseViewModel() {

    private val selectingModulesInteractor by api(ISelectingModulesInteractorApi::selectingModulesInteractor)
    private val recruitmentInteractor by api(IRecruitmentInteractorApi::recruitmentInteractor)

    val picturesState: StateResultSubject<List<String>> = emptyResultBehaviorSubject()

    val userInfoState: ResultSubject<User> by lazyEmptyResultPublishSubject()
    val statusState: ResultSubject<List<Status>> by lazyEmptyResultPublishSubject()

    fun onOpen() {
        fetchStatusList()
        getUserInfo()
    }

    private fun fetchStatusList() {
        Timber.d("getRecruitmentInfo()")

        statusState.onLoadingState()
        recruitmentInteractor
            .getRecruitmentInfo()
            .schedule(
                recruitmentChannel,
                onSuccess = { list ->
                    Timber.d("fetchStatusList(): list - ${list.data}")
                    statusState.onNext(list)
                },
                onError = Timber::e
            )
    }

    @Suppress("UnusedPrivateMember") // TODO: Remove once implemented
    fun changeEmployeeStatus(employee: Employee, status: Status) {
        // TODO: Add change of status logic and update statusState
    }

    @Suppress("UnusedPrivateMember") // TODO: Remove once implemented
    fun createNewStatus(statusName: String, imageLink: String) {
        // TODO: Add create status logic
    }

    private fun getUserInfo() {
        Timber.d("getUserInfo()")

        userInfoState.onLoadingState()
        selectingModulesInteractor
            .getUserInfo()
            .schedule(
                userInfoChannel,
                onSuccess = { result ->
                    Timber.d("getUserInfo(): result = $result")
                    userInfoState.onNext(result)
                },
                onError = Timber::e
            )
    }

    companion object {
        val recruitmentChannel = Channel()
        val userInfoChannel = Channel()
    }
}
