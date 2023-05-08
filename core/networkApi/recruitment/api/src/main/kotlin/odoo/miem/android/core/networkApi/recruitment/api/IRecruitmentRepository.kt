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
    fun getRecruitmentInfo(): Single<RecruitmentResponse>

    /**
     * Recruitment Jobs
     */
    fun getRecruitmentJobsInfo(): Single<RecruitmentJobsResponse>

    fun setJobPublication(jobId: Int, publish: Boolean): Single<Boolean>

    fun setJobFavoritable(jobId: Int, isFavorite: Boolean): Single<Boolean>

    fun setJobRecruit(jobId: Int, isRecruitingDone: Boolean): Single<Boolean>
}
