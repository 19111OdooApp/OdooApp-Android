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
                SuccessResult(response.toListDTO().sortedBy { !it.isFavorite })
            }
            .onErrorReturn {
                Timber.e("getRecruitmentJobs(): error message = ${it.message}")
                ErrorResult(
                    message = R.string.general_authorization_error,
                    isSessionExpired = ErrorResult.isSessionExpiredMessage(it.message)
                )
            }
    }

    override fun setJobPublication(jobId: Long, publish: Boolean): ResultSingle<Boolean> {
        Timber.d("setJobPublication()")

        return recruitmentRepository
            .setJobPublication(jobId, publish)
            .map<Result<Boolean>> { response ->
                Timber.d("setJobPublication(): get response - $response")
                SuccessResult(response)
            }
            .onErrorReturn {
                Timber.e("setJobPublication(): error message = ${it.message}")
                ErrorResult(
                    message = R.string.general_authorization_error,
                    isSessionExpired = ErrorResult.isSessionExpiredMessage(it.message)
                )
            }
    }

    override fun setJobFavoritable(jobId: Long, isFavorite: Boolean): ResultSingle<Boolean> {
        Timber.d("setJobFavoritable()")

        return recruitmentRepository
            .setJobFavoritable(jobId, isFavorite)
            .map<Result<Boolean>> { response ->
                Timber.d("setJobFavoritable(): get response - $response")
                SuccessResult(response)
            }
            .onErrorReturn {
                Timber.e("setJobFavoritable(): error message = ${it.message}")
                ErrorResult(
                    message = R.string.general_authorization_error,
                    isSessionExpired = ErrorResult.isSessionExpiredMessage(it.message)
                )
            }
    }

    override fun setJobRecruit(jobId: Long, isRecruitingDone: Boolean): ResultSingle<Boolean> {
        Timber.d("setJobRecruit()")

        return recruitmentRepository
            .setJobRecruit(jobId, isRecruitingDone)
            .map<Result<Boolean>> { response ->
                Timber.d("setJobRecruit(): get response - $response")
                SuccessResult(response)
            }
            .onErrorReturn {
                Timber.e("setJobRecruit(): error message = ${it.message}")
                ErrorResult(
                    message = R.string.general_authorization_error,
                    isSessionExpired = ErrorResult.isSessionExpiredMessage(it.message)
                )
            }
    }

    private fun RecruitmentJobsResponse.toListDTO(): List<RecruitmentJob> =
        jobs?.fold(mutableListOf()) { list, job ->
            job.id?.let {
                list.add(
                    RecruitmentJob(
                        id = it,
                        name = job.name ?: DEFAULT_UNTITLED_JOB,
                        state = when (job.state) {
                            "open" -> RecruitmentJobState.RECRUIT_DONE
                            else -> RecruitmentJobState.RECRUIT_START
                        },
                        isFavorite = job.isFavorite ?: false,
                        numberToRecruit = job.numberToRecruit ?: DEFAULT_NUMBER,
                        numberOfNewApplication = job.numberOfNewApplication ?: DEFAULT_NUMBER,
                        numberOfApplication = job.numberOfApplication ?: DEFAULT_NUMBER,
                        url = "${dataStore.url}${job.websiteUrl?.drop(1)}",
                        isPublished = job.isPublished ?: false
                    )
                )
            }
            list
        } ?: emptyList()

    companion object {
        const val DEFAULT_UNTITLED_JOB = "Untitled Job"
        const val DEFAULT_NUMBER = 0
    }
}
