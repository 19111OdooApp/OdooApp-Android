package odoo.miem.android.core.networkApi.crm.api

import io.reactivex.rxjava3.core.Single
import odoo.miem.android.core.networkApi.crm.api.entities.CrmtKanbanStagesResponse
import odoo.miem.android.core.networkApi.crm.api.entities.CrmResponse

/**
 * [ICrmRepository] - interface for wrapping data layer
 * logic, which is connected with crm
 *
 * @author Vorozhtos Mikhail
 */
interface ICrmRepository {

    /**
     * Crm Kanban
     */
    fun getCrmKanbanInfo(): Single<CrmResponse> // TODO Change back

    fun getCrmKanbanStages(): Single<CrmtKanbanStagesResponse> // TODO Change back
//
//    fun createNewKanbanStatus(jobId: Long, topic: String): Single<List<Any>>
//
//    fun changeStageInRecruitmentKanban(stageId: Long, employeeId: Long): Single<Boolean>

    /**
     * Crm Application Details
     */
//    fun getApplicationInfo(applicationId: Long): Single<RecruitmentApplicationDetailsResponse>
}
