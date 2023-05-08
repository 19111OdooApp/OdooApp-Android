package odoo.miem.android.core.networkApi.recruitment.api

import io.reactivex.rxjava3.core.Single
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentJobsResponse
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentResponse

/**
 * [IRecruitmentRepository] - interface for wrapping data layer
 * logic, which is connected with recruitment
 *
 * @author Vorozhtos Mikhail
 */
interface IRecruitmentRepository {

    /**
     * Recruitment Kanban
     */
    fun getRecruitmentKanbanInfo(jobId: Long): Single<RecruitmentResponse>

    /**
     * Recruitment Jobs
     */
    fun getRecruitmentJobsInfo(): Single<RecruitmentJobsResponse>

    fun setJobPublication(jobId: Long, publish: Boolean): Single<Boolean>

    fun setJobFavoritable(jobId: Long, isFavorite: Boolean): Single<Boolean>

    fun setJobRecruit(jobId: Long, isRecruitingDone: Boolean): Single<Boolean>
}
