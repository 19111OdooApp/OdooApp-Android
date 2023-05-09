package odoo.miem.android.feature.recruitment.impl

import androidx.compose.runtime.mutableStateListOf
import io.reactivex.rxjava3.subjects.PublishSubject
import odoo.miem.android.common.network.recruitment.api.di.IRecruitmentInteractorApi
import odoo.miem.android.common.network.recruitment.api.entities.jobs.RecruitmentJob
import odoo.miem.android.common.network.recruitment.api.entities.jobs.RecruitmentJobState
import odoo.miem.android.common.network.recruitment.api.entities.kanban.Employee
import odoo.miem.android.common.network.recruitment.api.entities.kanban.Status
import odoo.miem.android.common.network.selectingModules.api.di.ISelectingModulesInteractorApi
import odoo.miem.android.common.network.selectingModules.api.entities.User
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.platform.presentation.BaseViewModel
import odoo.miem.android.core.utils.rx.lazyEmptyResultPublishSubject
import odoo.miem.android.core.utils.rx.onLoadingState
import odoo.miem.android.core.utils.state.ErrorResult
import odoo.miem.android.core.utils.state.ResultSubject
import timber.log.Timber

internal class RecruitmentViewModel : BaseViewModel() {

    private val selectingModulesInteractor by api(ISelectingModulesInteractorApi::selectingModulesInteractor)

    private val recruitmentInteractor by api(IRecruitmentInteractorApi::recruitmentInteractor)
    private val recruitmentJobsInteractor by api(IRecruitmentInteractorApi::recruitmentJobsInteractor)

    val userInfoState: ResultSubject<User> by lazyEmptyResultPublishSubject()
    val statusState: ResultSubject<List<Status>> by lazyEmptyResultPublishSubject()
    val changeStatusState: ResultSubject<Boolean> by lazyEmptyResultPublishSubject()

    val jobsState: ResultSubject<List<RecruitmentJob>> by lazyEmptyResultPublishSubject()
    val jobsList = mutableStateListOf<RecruitmentJob>()

    /**
     * Recruitment Jobs
     */
    val focusedJob: PublishSubject<RecruitmentJob> by lazy { PublishSubject.create() }

    fun onOpenJobs() {
        jobsList.clear()

        fetchJobsList()
        getUserInfo()
    }

    fun fetchJobsList() {
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

    fun changePublicationState(job: RecruitmentJob) {
        val index = jobsList.indexOf(job)
        val previousState = jobsList[index].isPublished
        jobsList[index] = jobsList[index].copy(isPublished = !previousState)

        focusedJob.onNext(jobsList[index])

        recruitmentJobsInteractor.setJobPublication(
            jobId = job.id,
            publish = !previousState
        ).schedule(
            recruitmentJobsChannel,
            onSuccess = { response ->
                Timber.d("changePublicationState(): response - $response")
            },
            onError = Timber::e
        )
    }

    fun changeRecruitState(job: RecruitmentJob) {
        val index = jobsList.indexOf(job)
        val previousState = jobsList[index].state
        jobsList[index] = jobsList[index].copy(
            state = if (previousState == RecruitmentJobState.RECRUIT_DONE) {
                RecruitmentJobState.RECRUIT_START
            } else {
                RecruitmentJobState.RECRUIT_DONE
            }
        )

        focusedJob.onNext(jobsList[index])

        recruitmentJobsInteractor.setJobRecruit(
            jobId = job.id,
            isRecruitingDone = previousState != RecruitmentJobState.RECRUIT_DONE
        ).schedule(
            recruitmentJobsChannel,
            onSuccess = { response ->
                Timber.d("changeRecruitState(): response - $response")
            },
            onError = Timber::e
        )
    }

    fun changeFavoriteState(job: RecruitmentJob) {
        val index = jobsList.indexOf(job)
        val previousState = jobsList[index].isFavorite
        jobsList[index] = jobsList[index].copy(isFavorite = !previousState)

        recruitmentJobsInteractor.setJobFavoritable(
            jobId = job.id,
            isFavorite = !previousState
        ).schedule(
            recruitmentJobsChannel,
            onSuccess = { response ->
                Timber.d("changePublicationState(): response - $response")
            },
            onError = Timber::e
        )
    }

    /**
     * Recruitment Kanban
     */
    fun fetchStatusList(jobId: Long) {
        Timber.d("fetchStatusList()")

        statusState.onLoadingState()
        recruitmentInteractor
            .getRecruitmentKanbanInfo(jobId)
            .schedule(
                recruitmentKanbanChannel,
                onSuccess = { list ->
                    Timber.d("fetchStatusList(): list - ${list.data}")
                    statusState.onNext(list)
                },
                onError = Timber::e
            )
    }

    fun changeEmployeeStatus(jobId: Long, employee: Employee, status: Status) {
        recruitmentInteractor
            .changeStageInRecruitmentKanban(
                stageId = status.id,
                employeeId = employee.id
            )
            .schedule(
                recruitmentKanbanChannel,
                onSuccess = { result ->
                    Timber.d("changeEmployeeStatus(): result - $result")
                    if (result is ErrorResult) {
                        changeStatusState.onNext(ErrorResult(R.string.error_recruitment_change_status))
                    } else {
                        fetchStatusList(jobId)
                        changeStatusState.onNext(result)
                    }
                },
                onError = Timber::e
            )
    }

    fun createNewStatus(jobId: Long, topic: String) {
        Timber.d("createNewStatus()")

        statusState.onLoadingState()
        recruitmentInteractor
            .createNewKanbanStatus(jobId, topic)
            .schedule(
                recruitmentKanbanChannel,
                onSuccess = { status ->
                    Timber.d("createNewStatus(): list - ${status.data}")
                    fetchStatusList(jobId)
                },
                onError = Timber::e
            )
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
        val recruitmentKanbanChannel = Channel()
        val recruitmentJobsChannel = Channel()
        val userInfoChannel = Channel()
    }
}
