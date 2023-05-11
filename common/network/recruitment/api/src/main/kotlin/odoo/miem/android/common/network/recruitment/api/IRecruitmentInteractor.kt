package odoo.miem.android.common.network.recruitment.api

import odoo.miem.android.common.network.recruitment.api.entities.kanban.Status
import odoo.miem.android.core.utils.state.ResultSingle

/**
 * [IRecruitmentInteractor] - interface for handling logic
 * of recruitment kanban
 *
 * @author Vorozhtos Mikhail
 */
interface IRecruitmentInteractor {

    /**
     * [getRecruitmentKanbanInfo] gives a list of [Status] with [Employee]'s
     * by given [jobId]
     */
    fun getRecruitmentKanbanInfo(jobId: Long): ResultSingle<List<Status>>

    /**
     * [getRecruitmentKanbanInfo] created new status for job by [jobId] with
     * given [topic]
     *
     * @return list with id and name of new status
     */
    fun createNewKanbanStatus(jobId: Long, topic: String): ResultSingle<List<Any>>

    /**
     * [changeStageInRecruitmentKanban] change status of application with [employeeId] to new [stageId]
     *
     * @return true, if changed successfully
     */
    fun changeStageInRecruitmentKanban(stageId: Long, employeeId: Long): ResultSingle<Boolean>
}
