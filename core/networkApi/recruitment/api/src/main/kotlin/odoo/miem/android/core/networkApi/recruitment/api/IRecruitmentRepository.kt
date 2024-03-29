package odoo.miem.android.core.networkApi.recruitment.api

import io.reactivex.rxjava3.core.Single
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentApplicationDetailsResponse
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentJobsResponse
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentKanbanStagesResponse
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentLogNoteResponse
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentResponse
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentScheduleActivityResponse

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

    fun getRecruitmentKanbanStages(jobId: Long): Single<RecruitmentKanbanStagesResponse>

    fun createNewKanbanStatus(jobId: Long, topic: String): Single<List<Any>>

    fun changeStageInRecruitmentKanban(stageId: Long, employeeId: Long): Single<Boolean>

    /**
     * Recruitment Jobs
     */
    fun getRecruitmentJobsInfo(): Single<RecruitmentJobsResponse>

    fun setJobPublication(jobId: Long, publish: Boolean): Single<Boolean>

    fun setJobFavoritable(jobId: Long, isFavorite: Boolean): Single<Boolean>

    fun setJobRecruit(jobId: Long, isRecruitingDone: Boolean): Single<Boolean>

    /**
     * Recruitment Application Details
     */
    fun getApplicationInfo(applicationId: Long): Single<RecruitmentApplicationDetailsResponse>

    fun getLogNotes(userId: Long): Single<List<RecruitmentLogNoteResponse>>

    fun createLogNote(userId: Long, text: String): Single<Unit>

    fun getScheduleActivities(activityIds: List<Long>): Single<List<RecruitmentScheduleActivityResponse>>
}
