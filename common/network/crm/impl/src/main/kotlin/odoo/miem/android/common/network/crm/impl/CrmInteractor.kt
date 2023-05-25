package odoo.miem.android.common.network.crm.impl

import io.reactivex.rxjava3.core.Single
import odoo.miem.android.common.network.crm.api.ICrmInteractor
import odoo.miem.android.common.network.crm.api.entities.kanban.OpportunityCRM
import odoo.miem.android.common.network.crm.api.entities.kanban.StatusCRM
import odoo.miem.android.common.network.crm.impl.helper.MutableStatusCRM
import odoo.miem.android.common.network.crm.impl.helper.toStatusCRM
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.DeadlineStatus
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.networkApi.crm.api.di.ICrmRepositoryApi
import odoo.miem.android.core.networkApi.crm.api.entities.CrmKanbanStagesResponse
import odoo.miem.android.core.networkApi.crm.api.entities.CrmResponse
import odoo.miem.android.core.utils.state.ErrorResult
import odoo.miem.android.core.utils.state.Result
import odoo.miem.android.core.utils.state.ResultSingle
import odoo.miem.android.core.utils.state.SuccessResult
import timber.log.Timber
import javax.inject.Inject

/**
 * [CrmInteractor] - implementation of [ICrmInteractor]
 *
 * @author Vorozhtsov Mikhail
 */
class CrmInteractor @Inject constructor() : ICrmInteractor {

    private val crmRepository by api(ICrmRepositoryApi::crmRepository)

    override fun getCrmKanbanInfo(userId: Int): ResultSingle<List<StatusCRM>> {
        Timber.d("getCrmKanbanInfo()")

        return Single
            .zip(
                crmRepository.getCrmKanbanInfo(userId),
                crmRepository.getCrmKanbanStages(userId)
            ) { kanbanInfo: CrmResponse,
                rawStages: CrmKanbanStagesResponse ->

                Timber.d("getCrmKanbanInfo(): kanbanInfo = $kanbanInfo")
                Timber.d("getCrmKanbanInfo(): rawStages = $rawStages")

                // Firstly, make mutable status for current job
                var iconIdCounter = 0
                val mapOfStages = rawStages.stages
                    ?.mapNotNull { stage ->
                        val stageId = (stage.stageInfo?.getOrNull(0) as? Double)?.toLong()
                        val stageTopic = stage.stageInfo?.getOrNull(1).toString()
                        stageId?.let {
                            MutableStatusCRM(
                                statusName = stageTopic,
                                iconId = iconIdCounter++,
                                id = it
                            )
                        }
                    }
                    ?.associate { status -> status.id to status }
                    ?: emptyMap()

                Timber.d("getCrmKanbanInfo(): mutable stages = $mapOfStages")

                // Secondly, enhance filtered status with employees
                kanbanInfo.records?.forEach { record ->
                    (record.stageInfo?.firstOrNull() as? Double)?.let { id ->
                        mapOfStages[id.toLong()]?.employees?.add(
                            OpportunityCRM( // TODO Add more info
                                name = record.name.takeIf { !it.isNullOrEmpty() }
                                    ?: record.partnerNameInfo.getParam().takeIf { !it.isNullOrEmpty() }
                                    ?: "Cool name",
                                rating = record.rating?.toDoubleOrNull() ?: 0.0,
                                imageUrl = null,
                                id = checkNotNull(record.id),
                                deadlineStatus = record.activityState?.let {
                                    Timber.d("STATUS - $it")
                                    if (it == DEADLINE_STATUS_OVERDUE) {
                                        DeadlineStatus.EXPIRED
                                    } else {
                                        DeadlineStatus.ACTIVE
                                    }
                                } ?: DeadlineStatus.NO_TASKS,
                                iconId = mapOfStages[id.toLong()]?.iconId ?: 0,
                                expectedRevenue = record.expectedRevenue ?: 0.0
                            )
                        )
                    }
                }

                Timber.d("getCrmKanbanInfo(): final stages = $mapOfStages")

                mapOfStages.values.map { it.toStatusCRM() }
            }
            .map<Result<List<StatusCRM>>> { result ->
                SuccessResult(result)
            }
            .onErrorReturn {
                Timber.e("getCrmKanbanInfo(): error message = ${it.message}")
                ErrorResult(
                    isSessionExpired = ErrorResult.isSessionExpiredMessage(it.message)
                )
            }
    }

    override fun createNewCrmStatus(topic: String): ResultSingle<List<Any>> {
        Timber.d("createNewCrmStatus(): topic - $topic")

        return crmRepository
            .createNewCrmStatus(topic)
            .map<Result<List<Any>>> { response ->
                SuccessResult(response)
            }
            .onErrorReturn {
                Timber.e("createNewCrmStatus(): error message = ${it.message}")
                ErrorResult(
                    isSessionExpired = ErrorResult.isSessionExpiredMessage(it.message)
                )
            }
    }

    override fun changeStageInCrmKanban(stageId: Long, opportunityId: Long): ResultSingle<Boolean> {
        Timber.d("changeStageInCrmKanban(): stageId - $stageId, opportunityId - $opportunityId")

        return crmRepository
            .changeStageInCrmKanban(stageId, opportunityId)
            .map<Result<Boolean>> { response ->
                SuccessResult(response)
            }
            .onErrorReturn {
                Timber.e("changeStageInCrmKanban(): error message = ${it.message}")
                ErrorResult(
                    isSessionExpired = ErrorResult.isSessionExpiredMessage(it.message)
                )
            }
    }

    private fun List<Any>?.getParam(index: Int = 1) = this?.get(index)?.let { it as? String }

    companion object {
        const val DEADLINE_STATUS_OVERDUE = "overdue"
    }
}
