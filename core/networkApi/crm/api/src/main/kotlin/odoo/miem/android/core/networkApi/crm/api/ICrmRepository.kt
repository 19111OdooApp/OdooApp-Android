package odoo.miem.android.core.networkApi.crm.api

import io.reactivex.rxjava3.core.Single
import odoo.miem.android.core.networkApi.crm.api.entities.CrmApplicationDetailsResponse
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
    fun getCrmKanbanInfo(userId: Int): Single<CrmResponse>

    fun getCrmKanbanStages(userId: Int): Single<CrmKanbanStagesResponse>

    fun createNewCrmStatus(topic: String): Single<List<Any>>

    fun changeStageInCrmKanban(stageId: Long, opportunityId: Long): Single<Boolean>

    /**
     * Crm Opportunity Details
     */
    fun getOpportunityInfo(opportunityId: Long): Single<CrmApplicationDetailsResponse>
}
