package odoo.miem.android.common.network.recruitment.impl

import io.reactivex.rxjava3.core.Single
import odoo.miem.android.common.network.recruitment.api.IRecruitmentDetailsInteractor
import odoo.miem.android.common.network.recruitment.api.IRecruitmentInteractor
import odoo.miem.android.common.network.recruitment.api.entities.details.ApplicationInfo
import odoo.miem.android.common.network.recruitment.api.entities.details.LogNoteInfo
import odoo.miem.android.common.network.recruitment.api.entities.details.ScheduleActivityInfo
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.networkApi.recruitment.api.di.IRecruitmentRepositoryApi
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentApplicationDetailsResponse
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentLogNoteResponse
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentScheduleActivityResponse
import odoo.miem.android.core.utils.state.ErrorResult
import odoo.miem.android.core.utils.state.Result
import odoo.miem.android.core.utils.state.ResultSingle
import odoo.miem.android.core.utils.state.SuccessResult
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

/**
 * [RecruitmentDetailsInteractor] - implementation of [IRecruitmentInteractor]
 *
 * @author Vorozhtsov Mikhail
 */
class RecruitmentDetailsInteractor @Inject constructor() : IRecruitmentDetailsInteractor {

    private val recruitmentRepository by api(IRecruitmentRepositoryApi::recruitmentRepository)

    private val inputFormatter by lazy { SimpleDateFormat("yyyy-MM-dd HH:mm:ss") }
    private val outputFormatter by lazy { SimpleDateFormat("dd MMMM YYYY HH:mm:ss") }

    private val deadlineInputFormatter by lazy { SimpleDateFormat("yyyy-MM-dd") }
    private val deadlineOutputFormatter by lazy { SimpleDateFormat("dd MMMM") }

    override fun getApplicationInfo(applicationId: Long): ResultSingle<ApplicationInfo> {
        Timber.d("getApplicationInfo(): applicationId - $applicationId")

        return recruitmentRepository
            .getApplicationInfo(applicationId)
            .map { response ->
                Timber.d("getApplicationInfo(): get response - $response")
                response.toDTO()
            }
            .flatMap { applicationInfo ->
                recruitmentRepository.getLogNotes(applicationInfo.id)
                    .map { logNotes ->
                        Timber.d("List of notes: $logNotes")
                        applicationInfo.copy(logNotes = logNotes.map { it.toDTO() })
                    }
            }
            .flatMap { applicationInfo ->
                val activityIds = applicationInfo.activityIds
                if (activityIds?.isNotEmpty() == true) {
                    recruitmentRepository.getScheduleActivities(activityIds)
                        .map { scheduleActivities ->
                            Timber.d("List of activities: $scheduleActivities")
                            applicationInfo.copy(scheduleActivities = scheduleActivities.map { it.toDTO() })
                        }
                } else {
                    Single.just(applicationInfo)
                }
            }
            .map<Result<ApplicationInfo>> {
                SuccessResult(it)
            }
            .onErrorReturn {
                Timber.e("getRecruitmentJobs(): error message = ${it.message}")
                ErrorResult(
                    isSessionExpired = ErrorResult.isSessionExpiredMessage(it.message)
                )
            }
    }

    override fun createLogNote(userId: Long, text: String): ResultSingle<Unit> {
        Timber.d("createLogNote(): userId - $userId, text - $text")

        return recruitmentRepository
            .createLogNote(userId, text)
            .map<Result<Unit>> { SuccessResult() }
            .onErrorReturn {
                Timber.e("createLogNote(): error message = ${it.message}")
                ErrorResult(
                    isSessionExpired = ErrorResult.isSessionExpiredMessage(it.message)
                )
            }
    }

    private fun RecruitmentApplicationDetailsResponse.toDTO() = ApplicationInfo(
        id = checkNotNull(id),
        stageInfo = stageInfo,
        employeeName = partnerName?.takeIf { it.isNotEmpty() } ?: employeeName,
        employeeEmail = employeeEmail,
        employeePhone = employeePhone,
        employeeMobile = employeeMobile,
        employeeFullName = employeeFullNameInfo.getParam(),
        employeeTestTask = employeeTestTaskInfo.getParam(),
        employeeProject = employeeProjectInfo.getParam(),
        employeeGroup = employeeGroupInfo.getParam(),
        employeeSpecialization = employeeSpecializationInfo.getParam(),
        recruiterName = recruiterInfo.getParam(),
        rating = rating?.toDoubleOrNull() ?: 0.0,
        recruiterSourceName = recruiterSourceInfo.getParam(),
        jobName = jobInfo.getParam(),
        departmentName = departmentInfo.getParam(),
        salaryExpected = salaryExpected,
        salaryProposed = salaryProposed,
        employeeSummary = employeeSummary,
        activityIds = activityIds,
        messageIds = messageIds,
    )

    private fun RecruitmentLogNoteResponse.toDTO() = LogNoteInfo(
        id = checkNotNull(id),
        date = date?.let { date ->
            val inputDate = inputFormatter.parse(date)
            inputDate?.let {
                outputFormatter.format(it)
            } ?: ""
        } ?: "",
        message = message,
        authorId = authorInfo.getParam(0)?.toLongOrNull(),
        authorName = authorInfo.getParam(),
        postType = postInfo.getParam(),
        trackingInfoList = trackingInfoList ?: emptyList(),
        subtypeDescription = subtypeDescription,
    )

    private fun RecruitmentScheduleActivityResponse.toDTO() = ScheduleActivityInfo(
        id = checkNotNull(id),
        activityName = activityInfo.getParam(),
        note = note,
        createUserId = createUserInfo.getParam(0)?.toLongOrNull(),
        createUserName = createUserInfo.getParam(),
        assignUserId = assignUserInfo.getParam(0)?.toLongOrNull(),
        assignUserName = assignUserInfo.getParam(),
        createDate = createDate?.let { date ->
            val inputDate = inputFormatter.parse(date)
            inputDate?.let {
                outputFormatter.format(it)
            } ?: ""
        } ?: "",
        deadline = deadline?.let { date ->
            val inputDate = deadlineInputFormatter.parse(date)
            inputDate?.let {
                deadlineOutputFormatter.format(it)
            } ?: ""
        } ?: "",
        state = state?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        } ?: "Not set"
    )

    private fun List<Any>?.getParam(index: Int = 1) = this?.get(index)?.let { it as? String }
}
