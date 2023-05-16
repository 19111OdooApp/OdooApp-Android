package odoo.miem.android.core.networkApi.crm.impl

import io.reactivex.rxjava3.core.Single
import odoo.miem.android.core.jsonRpcApiFabric.jsonRpcApi
import odoo.miem.android.core.networkApi.crm.api.ICrmRepository
import odoo.miem.android.core.networkApi.crm.impl.source.ICrmDetailsService
import odoo.miem.android.core.networkApi.crm.impl.source.ICrmService
import odoo.miem.android.core.networkApi.crm.api.entities.CrmtKanbanStagesResponse
import odoo.miem.android.core.networkApi.crm.api.entities.CrmResponse
import javax.inject.Inject

/**
 * [CrmRepository] - implementation of [IRecruitmentRepository]
 *
 * @author Vorozhtsov Mikhail
 */
class CrmRepository @Inject constructor() : ICrmRepository {

    private val crmService by jsonRpcApi<ICrmService>()
    private val crmDetailsService by jsonRpcApi<ICrmDetailsService>()

    /**
     * Crm Kanban
     */
    override fun getCrmKanbanInfo(): Single<CrmResponse> {
        TODO("Not yet implemented")
    }

    override fun getCrmKanbanStages(): Single<CrmtKanbanStagesResponse> {
        TODO("Not yet implemented")
    }

}
