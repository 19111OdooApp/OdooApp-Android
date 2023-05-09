package odoo.miem.android.common.network.recruitment.api

import odoo.miem.android.common.network.recruitment.api.entities.kanban.Status
import odoo.miem.android.core.utils.state.ResultSingle

/**
 * [IRecruitmentInteractor] - interface for // TODO Descr
 *
 * @author Vorozhtos Mikhail
 */
interface IRecruitmentInteractor {

    // TODO Desc
    fun getRecruitmentKanbanInfo(jobId: Long): ResultSingle<List<Status>>

    fun createNewKanbanStatus(jobId: Long, topic: String): ResultSingle<List<Any>>

    fun changeStageInRecruitmentKanban(stageId: Long, employeeId: Long): ResultSingle<Boolean>
}
