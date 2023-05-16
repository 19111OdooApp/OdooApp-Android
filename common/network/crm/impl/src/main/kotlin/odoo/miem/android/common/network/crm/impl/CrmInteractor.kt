package odoo.miem.android.common.network.crm.impl

import odoo.miem.android.common.network.crm.api.ICrmInteractor
import odoo.miem.android.common.network.crm.api.entities.kanban.StatusCRM
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.networkApi.crm.api.di.ICrmRepositoryApi
import odoo.miem.android.core.utils.state.ResultSingle
import javax.inject.Inject

/**
 * [CrmInteractor] - implementation of [ICrmInteractor]
 *
 * @author Vorozhtsov Mikhail
 */
class CrmInteractor @Inject constructor() : ICrmInteractor {

    private val crmRepository by api(ICrmRepositoryApi::crmRepository)

    override fun getCrmKanbanInfo(jobId: Long): ResultSingle<List<StatusCRM>> {
        TODO("Not yet implemented")
    }

    override fun createNewCrmStatus(jobId: Long, topic: String): ResultSingle<List<Any>> {
        TODO("Not yet implemented")
    }

    override fun changeStageInCrmKanban(stageId: Long, employeeId: Long): ResultSingle<Boolean> {
        TODO("Not yet implemented")
    }
}
