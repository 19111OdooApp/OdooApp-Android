package odoo.miem.android.feature.recruitment.impl

import androidx.compose.runtime.mutableStateListOf
import odoo.miem.android.common.network.recruitment.api.di.IRecruitmentInteractorApi
import odoo.miem.android.common.network.recruitment.api.entities.jobs.RecruitmentJob
import odoo.miem.android.common.network.recruitment.api.entities.kanban.Employee
import odoo.miem.android.common.network.recruitment.api.entities.kanban.Status
import odoo.miem.android.common.network.selectingModules.api.di.ISelectingModulesInteractorApi
import odoo.miem.android.common.network.selectingModules.api.entities.User
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.platform.presentation.BaseViewModel
import odoo.miem.android.core.utils.rx.lazyEmptyResultPublishSubject
import odoo.miem.android.core.utils.rx.onLoadingState
import odoo.miem.android.core.utils.state.ResultSubject
import timber.log.Timber

internal class RecruitmentViewModel : BaseViewModel() {

    private val selectingModulesInteractor by api(ISelectingModulesInteractorApi::selectingModulesInteractor)

    private val recruitmentInteractor by api(IRecruitmentInteractorApi::recruitmentInteractor)
    private val recruitmentJobsInteractor by api(IRecruitmentInteractorApi::recruitmentJobsInteractor)

    val userInfoState: ResultSubject<User> by lazyEmptyResultPublishSubject()
    val statusState: ResultSubject<List<Status>> by lazyEmptyResultPublishSubject()

    val jobsState: ResultSubject<List<RecruitmentJob>> by lazyEmptyResultPublishSubject()
    val jobsList = mutableStateListOf<RecruitmentJob>()

    /**
     * Recruitment Jobs
     */
    private var shouldJobsReload = true

    fun onOpenJobs() {
        if (!shouldJobsReload) return

        fetchJobsList()
        getUserInfo()

        shouldJobsReload = false
    }

    private fun fetchJobsList() {
        Timber.d("fetchJobsList()")

        statusState.onLoadingState()
        recruitmentJobsInteractor
            .getRecruitmentJobs()
            .schedule(
                recruitmentJobsChannel,
                onSuccess = { list ->
                    Timber.d("fetchJobsList(): list - ${list.data}")
                    jobsList.addAll(list.data ?: emptyList())
                    jobsState.onNext(list)
                },
                onError = Timber::e
            )
    }

    /**
     * Recruitment Kanban
     */
    fun fetchStatusList() {
        Timber.d("fetchStatusList()")

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

    fun createNewStatus(statusName: String) {
        // TODO
        Timber.d("New status name - $statusName")
    }

    /**
     * User info
     */
    fun getUserInfo() {
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
        val recruitmentJobsChannel = Channel()
        val userInfoChannel = Channel()
    }
}
