package odoo.miem.android.common.network.crm.api

import odoo.miem.android.common.network.crm.api.entities.kanban.StatusCRM
import odoo.miem.android.core.utils.state.ResultSingle

/**
 * [ICrmInteractor] - interface for handling logic
 * of recruitment kanban
 *
 * @author Vorozhtos Mikhail
 */
interface ICrmInteractor {

    /**
     * [getRecruitmentKanbanInfo] gives a list of [Status] with [Employee]'s
     * by given [jobId]
     */
    fun getCrmKanbanInfo(userId: Int): ResultSingle<List<StatusCRM>>

    /**
     * [createNewCrmStatus] created new status for crm with
     * given [topic]
     *
     * @return list with id and name of new status
     */
    fun createNewCrmStatus(topic: String): ResultSingle<List<Any>>

    /**
     * [changeStageInCrmKanban] change status of application with [stageId] to new [opportunityId]
     *
     * @return true, if changed successfully
     */
    fun changeStageInCrmKanban(stageId: Long, opportunityId: Long): ResultSingle<Boolean>
}
