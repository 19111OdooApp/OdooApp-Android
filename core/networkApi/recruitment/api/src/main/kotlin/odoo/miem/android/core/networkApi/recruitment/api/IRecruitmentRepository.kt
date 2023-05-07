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
}
