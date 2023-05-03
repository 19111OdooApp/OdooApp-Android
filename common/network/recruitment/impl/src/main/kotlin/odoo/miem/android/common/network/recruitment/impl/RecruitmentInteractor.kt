package odoo.miem.android.common.network.recruitment.impl

import odoo.miem.android.common.network.recruitment.api.IRecruitmentInteractor
import odoo.miem.android.common.network.recruitment.api.entities.Employee
import odoo.miem.android.common.network.recruitment.api.entities.Status
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.DeadlineStatus
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.networkApi.recruitment.api.di.IRecruitmentRepositoryApi
import odoo.miem.android.core.utils.state.ErrorResult
import odoo.miem.android.core.utils.state.Result
import odoo.miem.android.core.utils.state.ResultSingle
import odoo.miem.android.core.utils.state.SuccessResult
import timber.log.Timber
import javax.inject.Inject

/**
 * [RecruitmentInteractor] - implementation of [IAuthorizationInteractor]
 *
 * @author Vorozhtsov Mikhail
 */
class RecruitmentInteractor @Inject constructor() : IRecruitmentInteractor {

    private val recruitmentRepository by api(IRecruitmentRepositoryApi::recruitmentRepository)

    override fun getRecruitmentInfo(): ResultSingle<List<Status>> {
        Timber.d("getRecruitmentInfo()")

        return recruitmentRepository
            .getRecruitmentInfo()
            .map<Result<List<Status>>> { response ->
                Timber.d("getRecruitmentInfo(): response = $response")
                var counter = 0
                val list = response.records
                    ?.groupBy { it.stageInfo?.getOrNull(1) as? String }
                    ?.mapNotNull { (key, value) ->
                        key?.let {
                            Status(
                                statusName = key,
                                employees = value.map { record ->
                                    Employee(
                                        name = record.name ?: "Cool name",
                                        rating = record.rating?.toDouble() ?: 0.0,
                                        imageUrl = null,
                                        id = record.id,
                                        deadlineStatus = record.activityState?.let {
                                            Timber.d("STATUS - $it")
                                            if (it == DEADLINE_STATUS_OVERDUE) {
                                                DeadlineStatus.EXPIRED
                                            } else {
                                                DeadlineStatus.ACTIVE
                                            }
                                        } ?: DeadlineStatus.NO_TASKS,
                                        statusId = counter
                                    )
                                },
                                id = counter++
                            )
                        }
                    }
                Timber.d("getRecruitmentInfo(): final list - $list")
                SuccessResult(list)
            }
            .onErrorReturn {
                Timber.e("getRecruitmentInfo(): error message = ${it.message}")
                ErrorResult(R.string.general_authorization_error)
            }
    }

    companion object {
        const val DEADLINE_STATUS_OVERDUE = "overdue"
    }
}
