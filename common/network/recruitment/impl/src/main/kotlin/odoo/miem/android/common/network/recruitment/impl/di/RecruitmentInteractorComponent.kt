package odoo.miem.android.common.network.recruitment.impl.di

import dagger.Component
import odoo.miem.android.common.network.recruitment.api.di.IRecruitmentInteractorApi
import odoo.miem.android.common.network.recruitment.impl.RecruitmentInteractor

/**
 * [RecruitmentInteractorComponent] - **Dagger** component, which implements interface [IRecruitmentInteractorApi]
 * Providing in general **DI graph** with a help of [RecruitmentInteractorApiProvider].
 *
 * Included modules:
 *  - [RecruitmentInteractorModule] - provides [RecruitmentInteractor] in *DI graph*
 *
 * @author Vorozhtsov Mikhail
 */
@Component(
    modules = [
        RecruitmentInteractorModule::class
    ]
)
interface RecruitmentInteractorComponent : IRecruitmentInteractorApi {
    companion object {
        fun create(): IRecruitmentInteractorApi = DaggerRecruitmentInteractorComponent.builder().build()
    }
}
