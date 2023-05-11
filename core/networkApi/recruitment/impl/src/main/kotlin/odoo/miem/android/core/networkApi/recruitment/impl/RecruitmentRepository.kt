package odoo.miem.android.core.networkApi.recruitment.impl

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import odoo.miem.android.core.jsonRpcApiFabric.jsonRpcApi
import odoo.miem.android.core.networkApi.recruitment.api.IRecruitmentRepository
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentJobsResponse
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentKanbanStagesResponse
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentResponse
import odoo.miem.android.core.networkApi.recruitment.impl.source.IRecruitmentJobsService
import odoo.miem.android.core.networkApi.recruitment.impl.source.IRecruitmentService
import timber.log.Timber
import javax.inject.Inject

/**
 * [RecruitmentRepository] - implementation of [IRecruitmentRepository]
 *
 * @author Vorozhtsov Mikhail
 */
class RecruitmentRepository @Inject constructor() : IRecruitmentRepository {

    private val recruitmentService by jsonRpcApi<IRecruitmentService>()
    private val recruitmentJobsService by jsonRpcApi<IRecruitmentJobsService>()

    override fun getRecruitmentKanbanInfo(jobId: Long): Single<RecruitmentResponse> {
        Timber.d("getRecruitmentInfo()")

        return Single.fromCallable {
            recruitmentService.getRecruitmentInfo(
                domain = listOf(
                    listOf("job_id", "=", jobId)
                )
            )
        }.subscribeOn(Schedulers.io())
    }

    override fun getRecruitmentKanbanStages(jobId: Long): Single<RecruitmentKanbanStagesResponse> {
        Timber.d("getRecruitmentKanbanStages()")

        return Single.fromCallable {
            recruitmentService.getRecruitmentKanbanStages(
                kwargs = mapOf(
                    "domain" to listOf(listOf("job_id", "=", jobId)),
                    "fields" to listOf("stage_id"),
                    "groupby" to listOf("stage_id"),
                )
            )
        }.subscribeOn(Schedulers.io())
    }

    override fun createNewKanbanStatus(jobId: Long, topic: String): Single<List<Any>> {
        Timber.d("getRecruitmentInfo()")

        return Single.fromCallable {
            recruitmentService.createNewKanbanStatus(
                args = listOf(topic),
                kwargs = mapOf(
                    "context" to mapOf(
                        "default_job_id" to jobId
                    )
                )
            )
        }.subscribeOn(Schedulers.io())
    }

    override fun changeStageInRecruitmentKanban(stageId: Long, employeeId: Long): Single<Boolean> {
        Timber.d("getRecruitmentInfo()")

        return Single.fromCallable {
            recruitmentService.changeStageInRecruitmentKanban(
                args = listOf(
                    listOf(employeeId),
                    listOf("stage_id", stageId)
                ),
            )
        }.subscribeOn(Schedulers.io())
    }

    override fun getRecruitmentJobsInfo(): Single<RecruitmentJobsResponse> {
        Timber.d("getRecruitmentJobsInfo()")

        return Single.fromCallable {
            recruitmentJobsService.getRecruitmentJobs()
        }.subscribeOn(Schedulers.io())
    }

    override fun setJobPublication(jobId: Long, publish: Boolean): Single<Boolean> {
        Timber.d("setJobPublication(): jobId - $jobId, publish - $publish")

        return Single.fromCallable {
            recruitmentJobsService.editJob(
                args = listOf(
                    listOf(jobId),
                    mapOf(
                        "is_published" to publish,
                        "website_published" to publish
                    )
                )
            )
        }.subscribeOn(Schedulers.io())
    }

    override fun setJobFavoritable(jobId: Long, isFavorite: Boolean): Single<Boolean> {
        Timber.d("setJobFavoritable(): jobId - $jobId, isFavorite - $isFavorite")

        return Single.fromCallable {
            recruitmentJobsService.editJob(
                args = listOf(
                    listOf(jobId),
                    mapOf(
                        "is_favorite" to isFavorite
                    )
                )
            )
        }.subscribeOn(Schedulers.io())
    }

    override fun setJobRecruit(jobId: Long, isRecruitingDone: Boolean): Single<Boolean> {
        Timber.d("setJobRecruit(): jobId - $jobId, isRecruitingDone - $isRecruitingDone")

        return Single.fromCallable {
            recruitmentJobsService.editJob(
                args = listOf(
                    listOf(jobId)
                ),
                method = if (isRecruitingDone) "set_open" else "set_recruit"
            )
        }.subscribeOn(Schedulers.io())
    }
}
