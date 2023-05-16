package odoo.miem.android.core.networkApi.crm.impl

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import odoo.miem.android.core.jsonRpcApiFabric.jsonRpcApi
import odoo.miem.android.core.networkApi.crm.api.ICrmRepository
import odoo.miem.android.core.networkApi.crm.api.entities.CrmKanbanStagesResponse
import odoo.miem.android.core.networkApi.crm.api.entities.CrmResponse
import odoo.miem.android.core.networkApi.crm.impl.source.ICrmService
import timber.log.Timber
import javax.inject.Inject

/**
 * [CrmRepository] - implementation of [IRecruitmentRepository]
 *
 * @author Vorozhtsov Mikhail
 */
class CrmRepository @Inject constructor() : ICrmRepository {

    private val crmService by jsonRpcApi<ICrmService>()

    // TODO Return
//    private val crmDetailsService by jsonRpcApi<ICrmDetailsService>()

    /**
     * Crm Kanban
     */
    override fun getCrmKanbanInfo(userId: Int): Single<CrmResponse> {
        Timber.d("getCrmKanbanInfo()")

        return Single.fromCallable {
            crmService.getCrmInfo(
                domain = listOf(
                    "&",
                    listOf("type", "=", "opportunity"),
                    listOf("user_id", "=", userId),
                ),
            )
        }.subscribeOn(Schedulers.io())
    }

    override fun getCrmKanbanStages(userId: Int): Single<CrmKanbanStagesResponse> {
        Timber.d("getCrmKanbanStages()")

        return Single.fromCallable {
            crmService.getCrmKanbanStages(
                kwargs = mapOf(
                    "domain" to listOf(
                        "&",
                        listOf("type", "=", "opportunity"),
                        listOf("user_id", "=", userId),
                    ),
                    "fields" to listOf("stage_id"),
                    "groupby" to listOf("stage_id"),
                )
            )
        }.subscribeOn(Schedulers.io())
    }

    override fun createNewCrmStatus(topic: String): Single<List<Any>> {
        Timber.d("createNewCrmStatus()")

        return Single.fromCallable {
            crmService.createNewKanbanStatus(
                args = listOf(topic),
                kwargs = mapOf(
                    "context" to mapOf(
                        "default_type" to "opportunity"
                    )
                )
            )
        }.subscribeOn(Schedulers.io())
    }

    override fun changeStageInCrmKanban(stageId: Long, opportunityId: Long): Single<Boolean> {
        Timber.d("changeStageInCrmKanban(): stageId - $stageId, opportunityId - $opportunityId")

        return Single.fromCallable {
            crmService.changeStageInCrmKanban(
                args = listOf(
                    listOf(opportunityId),
                    mapOf("stage_id" to stageId)
                ),
            )
        }.subscribeOn(Schedulers.io())
    }
}
