package odoo.miem.android.common.network.recruitment.impl

import io.reactivex.rxjava3.core.Single
import odoo.miem.android.common.network.recruitment.api.IRecruitmentInteractor
import odoo.miem.android.common.network.recruitment.api.entities.kanban.Employee
import odoo.miem.android.common.network.recruitment.api.entities.kanban.Status
import odoo.miem.android.common.network.recruitment.impl.helper.MutableStatus
import odoo.miem.android.common.network.recruitment.impl.helper.toStatus
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.DeadlineStatus
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.networkApi.recruitment.api.di.IRecruitmentRepositoryApi
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentKanbanStagesResponse
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentResponse
import odoo.miem.android.core.utils.state.ErrorResult
import odoo.miem.android.core.utils.state.Result
import odoo.miem.android.core.utils.state.ResultSingle
import odoo.miem.android.core.utils.state.SuccessResult
import timber.log.Timber
import javax.inject.Inject

/**
 * [RecruitmentInteractor] - implementation of [IRecruitmentInteractor]
 *
 * @author Vorozhtsov Mikhail
 */
class RecruitmentInteractor @Inject constructor() : IRecruitmentInteractor {

    private val recruitmentRepository by api(IRecruitmentRepositoryApi::recruitmentRepository)

    override fun getRecruitmentKanbanInfo(jobId: Long): ResultSingle<List<Status>> {
        Timber.d("getRecruitmentInfo()")

        return Single
            .zip(
                recruitmentRepository.getRecruitmentKanbanInfo(jobId),
                recruitmentRepository.getRecruitmentKanbanStages(jobId)
            ) { kanbanInfo: RecruitmentResponse,
                rawStages: RecruitmentKanbanStagesResponse ->

                Timber.d("getRecruitmentInfo(): kanbanInfo = $kanbanInfo")
                Timber.d("getRecruitmentInfo(): rawStages = $rawStages")

                // Firstly, make mutable status for current job
                var iconIdcounter = 0
                val mapOfStages = rawStages.stages
                    ?.mapNotNull { stage ->
                        val stageId = (stage.stageInfo.getOrNull(0) as? Double)?.toLong()
                        val stageTopic = stage.stageInfo.getOrNull(1).toString()
                        stageId?.let {
                            MutableStatus(
                                statusName = stageTopic,
                                iconId = iconIdcounter++,
                                id = it
                            )
                        }
                    }
                    ?.associate { status -> status.id to status }
                    ?: emptyMap()

                Timber.d("getRecruitmentInfo(): mutable stages = $mapOfStages")

                // Secondly, enhance filtered status with employees
                kanbanInfo.records?.forEach { record ->
                    (record.stageInfo?.firstOrNull() as? Double)?.let { id ->
                        mapOfStages[id.toLong()]?.employees?.add(
                            Employee(
                                name = record.name ?: "Cool name",
                                rating = record.rating?.toDouble() ?: 0.0,
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
                                iconId = mapOfStages[id.toLong()]?.iconId ?: 0
                            )
                        )
                    }
                }

                Timber.d("getRecruitmentInfo(): final stages = $mapOfStages")

                mapOfStages.values.map { it.toStatus() }
            }
            .map<Result<List<Status>>> { result ->
                SuccessResult(result)
            }
            .onErrorReturn {
                Timber.e("getRecruitmentInfo(): error message = ${it.message}")
                ErrorResult(
                    message = R.string.general_authorization_error,
                    isSessionExpired = ErrorResult.isSessionExpiredMessage(it.message)
                )
            }
    }

    override fun createNewKanbanStatus(jobId: Long, topic: String): ResultSingle<List<Any>> {
        Timber.d("createNewKanbanStatus(): jobId - $jobId, topic - $topic")

        return recruitmentRepository
            .createNewKanbanStatus(jobId, topic)
            .map<Result<List<Any>>> { response ->
                SuccessResult(response)
            }
            .onErrorReturn {
                Timber.e("createNewKanbanStatus(): error message = ${it.message}")
                ErrorResult(
                    message = R.string.general_authorization_error,
                    isSessionExpired = ErrorResult.isSessionExpiredMessage(it.message)
                )
            }
    }

    override fun changeStageInRecruitmentKanban(
        stageId: Long,
        employeeId: Long
    ): ResultSingle<Boolean> {
        Timber.d("changeStageInRecruitmentKanban(): stageId - $stageId, employeeId - $employeeId")

        return recruitmentRepository
            .changeStageInRecruitmentKanban(stageId, employeeId)
            .map<Result<Boolean>> { response ->
                SuccessResult(response)
            }
            .onErrorReturn {
                Timber.e("changeStageInRecruitmentKanban(): error message = ${it.message}")
                ErrorResult(
                    message = R.string.general_authorization_error,
                    isSessionExpired = ErrorResult.isSessionExpiredMessage(it.message)
                )
            }
    }

    companion object {
        const val DEADLINE_STATUS_OVERDUE = "overdue"
    }
}
