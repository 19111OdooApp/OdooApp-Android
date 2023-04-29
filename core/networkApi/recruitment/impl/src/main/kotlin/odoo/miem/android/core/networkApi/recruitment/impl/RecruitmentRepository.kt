package odoo.miem.android.core.networkApi.recruitment.impl

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import odoo.miem.android.core.jsonRpcApiFabric.jsonRpcApi
import odoo.miem.android.core.networkApi.recruitment.api.IRecruitmentRepository
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentResponse
import odoo.miem.android.core.networkApi.recruitment.impl.source.IRecruitmentService
import timber.log.Timber
import javax.inject.Inject

/**
 * [RecruitmentRepository] - implementation of [IRecruitmentRepository]
 *
 * @author Vorozhtsov Mikhail
 */
class RecruitmentRepository @Inject constructor() : IRecruitmentRepository {

    private val recruitmentService by jsonRpcApi<IRecruitmentService>()

    override fun getRecruitmentInfo(): Single<RecruitmentResponse> {
        Timber.d("getRecruitmentInfo()")

        return Single.fromCallable {
            recruitmentService.getRecruitmentInfo()
        }.subscribeOn(Schedulers.io())
    }
}
