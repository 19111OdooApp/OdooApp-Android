package odoo.miem.android.core.networkApi.recruitment.impl.di

import dagger.Component
import odoo.miem.android.core.networkApi.recruitment.api.di.IRecruitmentRepositoryApi
import odoo.miem.android.core.networkApi.recruitment.impl.RecruitmentRepository

/**
 * [RecruitmentRepositoryComponent] - **Dagger** component, which implements interface [IAuthorizationRepositoryApi]
 * Providing in general **DI graph** with a help of [AuthorizationRepositoryApiProvider].
 *
 * Included modules:
 *  - [RecruitmentRepositoryModule] - provide [RecruitmentRepository] in *DI graph*
 *
 * @author Vorozhtsov Mikhail
 */
@Component(
    modules = [
        RecruitmentRepositoryModule::class
    ]
)
interface RecruitmentRepositoryComponent : IRecruitmentRepositoryApi {
    companion object {
        fun create(): IRecruitmentRepositoryApi = DaggerRecruitmentRepositoryComponent.builder()
            .build()
    }
}
