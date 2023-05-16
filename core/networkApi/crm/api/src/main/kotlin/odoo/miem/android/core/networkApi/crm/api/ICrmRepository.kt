package odoo.miem.android.core.networkApi.crm.api

import io.reactivex.rxjava3.core.Single
import odoo.miem.android.core.networkApi.crm.api.entities.CrmKanbanStagesResponse
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
    fun getCrmKanbanInfo(userId: Int): Single<CrmResponse> // TODO Change back

    fun getCrmKanbanStages(userId: Int): Single<CrmKanbanStagesResponse> // TODO Change back

    fun createNewCrmStatus(topic: String): Single<List<Any>>

    fun changeStageInCrmKanban(stageId: Long, opportunityId: Long): Single<Boolean>

    /**
     * Crm Application Details
     */
//    fun getApplicationInfo(applicationId: Long): Single<RecruitmentApplicationDetailsResponse>
}
