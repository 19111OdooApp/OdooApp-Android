package odoo.miem.android.common.network.recruitment.impl

import odoo.miem.android.common.network.recruitment.api.IRecruitmentInteractor
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.networkApi.recruitment.api.di.IRecruitmentRepositoryApi
import odoo.miem.android.core.utils.regex.getSessionIdFromCookie
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

    override fun getRecruitmentInfo(): ResultSingle<Unit> {
        Timber.d("getRecruitmentInfo()")

        return recruitmentRepository.getRecruitmentInfo()
            .map<Result<Unit>> { response ->
                Timber.d("getRecruitmentInfo(): response = $response")
                // TODO
                SuccessResult()
            }
            .onErrorReturn {
                Timber.e("generalAuthorization(): error message = ${it.message}")
                ErrorResult(R.string.general_authorization_error)
            }

//        recruitmentRepository
//            .getRecruitmentInfo(
//            ).schedule(
//                recruitmentChannel,
//                onSuccess = { response ->
//                    Timber.d("getRecruitmentInfo(): response - $response")
//                    val list = response.records
//                        .groupBy { it.stageInfo.getOrNull(1) as? String }
//                        .mapNotNull { (key, value) ->
//                            key?.let {
//                                Status(
//                                    statusName = key,
//                                    employees = value.map { record ->
//                                        Employee(
//                                            name = record.name,
//                                            rating = record.rating,
//                                            imageUrl = null,
//                                            id = record.id,
//                                            deadlineStatus = DeadlineStatus.ACTIVE
//                                        )
//                                    },
//                                    imageUrl = null
//                                )
//                            }
//                        }
//
//                    Timber.d("getRecruitmentInfo(): final list - $list")
//                    statusState.onNext(SuccessResult(list))
//                },
//                onError = Timber::e
//            )
    }
}
