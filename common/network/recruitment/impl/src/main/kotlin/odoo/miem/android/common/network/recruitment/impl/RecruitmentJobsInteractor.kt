package odoo.miem.android.common.network.recruitment.impl

import odoo.miem.android.common.network.recruitment.api.IRecruitmentJobsInteractor
import odoo.miem.android.common.network.recruitment.api.entities.jobs.RecruitmentJob
import odoo.miem.android.common.network.recruitment.api.entities.jobs.RecruitmentJobState
import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.networkApi.recruitment.api.di.IRecruitmentRepositoryApi
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentJobsResponse
import odoo.miem.android.core.utils.state.ErrorResult
import odoo.miem.android.core.utils.state.Result
import odoo.miem.android.core.utils.state.ResultSingle
import odoo.miem.android.core.utils.state.SuccessResult
import timber.log.Timber
import javax.inject.Inject

/**
 * [RecruitmentJobsInteractor] - implementation of [IRecruitmentJobsInteractor]
 *
 * @author Vorozhtsov Mikhail
 */
class RecruitmentJobsInteractor @Inject constructor() : IRecruitmentJobsInteractor {

    private val recruitmentRepository by api(IRecruitmentRepositoryApi::recruitmentRepository)
    private val dataStore by api(IDataStoreApi::dataStore)

    override fun getRecruitmentJobs(): ResultSingle<List<RecruitmentJob>> {
        Timber.d("getRecruitmentJobs()")

        return recruitmentRepository
            .getRecruitmentJobsInfo()
            .map<Result<List<RecruitmentJob>>> { response ->
                Timber.d("getRecruitmentJobs(): get response - $response")
                SuccessResult(response.toListDTO())
            }
            .onErrorReturn {
                Timber.e("getRecruitmentJobs(): error message = ${it.message}")
                ErrorResult(R.string.general_authorization_error)
            }
    }

    private fun RecruitmentJobsResponse.toListDTO(): List<RecruitmentJob> = jobs?.map {
        RecruitmentJob(
            id = it.id,
            name = it.name ?: DEFAULT_UNTITLED_JOB,
            state = when (it.state) {
                "open" -> RecruitmentJobState.RECRUIT_DONE
                else -> RecruitmentJobState.RECRUIT_START
            },
            isFavorite = it.isFavorite ?: false,
            numberToRecruit = it.numberToRecruit ?: DEFAULT_NUMBER,
            numberOfNewApplication = it.numberOfNewApplication ?: DEFAULT_NUMBER,
            numberOfApplication = it.numberOfApplication ?: DEFAULT_NUMBER,
            url = "${dataStore.url}${it.websiteUrl?.drop(1)}",
            isPublished = it.isPublished ?: false
        )
    } ?: emptyList()

    companion object {
        const val DEFAULT_UNTITLED_JOB = "Untitled Job"
        const val DEFAULT_NUMBER = 0
    }
}
