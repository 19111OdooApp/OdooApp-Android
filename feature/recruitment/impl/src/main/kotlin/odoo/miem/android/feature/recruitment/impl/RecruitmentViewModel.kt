package odoo.miem.android.feature.recruitment.impl

import androidx.compose.runtime.mutableStateListOf
import io.reactivex.rxjava3.subjects.PublishSubject
import odoo.miem.android.common.network.recruitment.api.di.IRecruitmentInteractorApi
import odoo.miem.android.common.network.recruitment.api.entities.details.ApplicationInfo
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
    private val recruitmentDetailsInteractor by api(IRecruitmentInteractorApi::recruitmentDetailsInteractor)

    val userInfoState: ResultSubject<User> by lazyEmptyResultPublishSubject()
    val statusState: ResultSubject<List<Status>> by lazyEmptyResultPublishSubject()
    val changeStatusState: ResultSubject<Boolean> by lazyEmptyResultPublishSubject()

    val jobsState: ResultSubject<List<RecruitmentJob>> by lazyEmptyResultPublishSubject()
    val jobsList = mutableStateListOf<RecruitmentJob>()

    val applicationInfoState: ResultSubject<ApplicationInfo> by lazyEmptyResultPublishSubject()

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
                recruitmentJobsGetChannel,
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
            recruitmentJobsChangePublicationChannel,
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
            recruitmentJobsChangeRecruitChannel,
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
            recruitmentJobsChangeFavoriteChannel,
            onSuccess = { response ->
                Timber.d("changePublicationState(): response - $response")
            },
            onError = Timber::e
        )
    }

    /**
     * Recruitment Kanban
     */
    fun onOpenKanban(jobId: Long) {
        fetchStatusList(jobId)
        getUserInfo()
    }

    fun fetchStatusList(jobId: Long) {
        Timber.d("fetchStatusList()")

        statusState.onLoadingState()
        recruitmentInteractor
            .getRecruitmentKanbanInfo(jobId)
            .schedule(
                recruitmentKanbanFetchStatusChannel,
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
                recruitmentKanbanChangeEmployeeChannel,
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
                recruitmentKanbanCreateStatusChannel,
                onSuccess = { status ->
                    Timber.d("createNewStatus(): list - ${status.data}")
                    fetchStatusList(jobId)
                },
                onError = Timber::e
            )
    }

    /**
     * Recruitment Application Details
     */

    fun onOpenDetails(applicationId: Long) {
        getApplicationInfo(applicationId)
    }

    fun getApplicationInfo(applicationId: Long) {
        Timber.d("getApplicationInfo(): applicationId - $applicationId")

        applicationInfoState.onLoadingState()
        recruitmentDetailsInteractor
            .getApplicationInfo(applicationId)
            .schedule(
                recruitmentApplicationInfoChannel,
                onSuccess = { details ->
                    Timber.d("createNewStatus(): details - $details")
                    applicationInfoState.onNext(details) // TODO...
                },
                onError = Timber::e
            )
    }

    fun createLogNote(applicationId: Long, text: String) {
        Timber.d("getApplicationInfo(): applicationId - $applicationId")

        applicationInfoState.onLoadingState()
        recruitmentDetailsInteractor
            .createLogNote(applicationId, text)
            .schedule(
                recruitmentCreateLogNoteChannel,
                onSuccess = { details ->
                    Timber.d("createLogNote(): details - $details")
                    getApplicationInfo(applicationId)
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
        // Jobs
        val recruitmentJobsGetChannel = Channel()
        val recruitmentJobsChangePublicationChannel = Channel()
        val recruitmentJobsChangeRecruitChannel = Channel()
        val recruitmentJobsChangeFavoriteChannel = Channel()

        // Kanban
        val recruitmentKanbanFetchStatusChannel = Channel()
        val recruitmentKanbanChangeEmployeeChannel = Channel()
        val recruitmentKanbanCreateStatusChannel = Channel()

        // Application Details
        val recruitmentApplicationInfoChannel = Channel()
        val recruitmentCreateLogNoteChannel = Channel()

        // User info
        val userInfoChannel = Channel()
    }
}
