package odoo.miem.android.common.network.recruitment.impl

import odoo.miem.android.common.network.recruitment.api.IRecruitmentDetailsInteractor
import odoo.miem.android.common.network.recruitment.api.IRecruitmentInteractor
import odoo.miem.android.common.network.recruitment.api.entities.details.ApplicationInfo
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.networkApi.recruitment.api.di.IRecruitmentRepositoryApi
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentApplicationDetailsResponse
import odoo.miem.android.core.utils.state.ErrorResult
import odoo.miem.android.core.utils.state.Result
import odoo.miem.android.core.utils.state.ResultSingle
import odoo.miem.android.core.utils.state.SuccessResult
import timber.log.Timber
import javax.inject.Inject

/**
 * [RecruitmentDetailsInteractor] - implementation of [IRecruitmentInteractor]
 *
 * @author Vorozhtsov Mikhail
 */
class RecruitmentDetailsInteractor @Inject constructor() : IRecruitmentDetailsInteractor {

    private val recruitmentRepository by api(IRecruitmentRepositoryApi::recruitmentRepository)

    override fun getApplicationInfo(applicationId: Long): ResultSingle<ApplicationInfo> {
        Timber.d("getApplicationInfo(): applicationId - $applicationId")

        return recruitmentRepository
            .getApplicationInfo(applicationId)
            .map<Result<ApplicationInfo>> { response ->
                Timber.d("getApplicationInfo(): get response - $response")
                SuccessResult(response.toDTO())
            }
            .onErrorReturn {
                Timber.e("getRecruitmentJobs(): error message = ${it.message}")
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

    private fun List<Any>?.getParam(index: Int = 1) = this?.get(index)?.let { it as? String }
}
