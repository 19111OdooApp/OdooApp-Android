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

    // TODO Remake

    /**
     * [getRecruitmentKanbanInfo] gives a list of [Status] with [Employee]'s
     * by given [jobId]
     */
    fun getCrmKanbanInfo(jobId: Long): ResultSingle<List<StatusCRM>>

    /**
     * [getRecruitmentKanbanInfo] created new status for job by [jobId] with
     * given [topic]
     *
     * @return list with id and name of new status
     */
    fun createNewCrmStatus(jobId: Long, topic: String): ResultSingle<List<Any>>

    /**
     * [changeStageInRecruitmentKanban] change status of application with [employeeId] to new [stageId]
     *
     * @return true, if changed successfully
     */
    fun changeStageInCrmKanban(stageId: Long, employeeId: Long): ResultSingle<Boolean>
}
