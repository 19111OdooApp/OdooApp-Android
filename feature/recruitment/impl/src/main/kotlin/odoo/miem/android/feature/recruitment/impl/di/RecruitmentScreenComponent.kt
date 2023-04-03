package odoo.miem.android.feature.recruitment.impl.di

import dagger.Component
import odoo.miem.android.feature.recruitment.api.di.IRecruitmentApi

/**
 * [RecruitmentScreenComponent] - **Dagger** component, which implements [IRecruitmentApi]
 * Providing in general **DI graph** with a help of [RecruitmentScreenApiProvider].
 *
 * Included modules:
 *  - [RecruitmentScreenModule] - provides [RecruitmentScreen]
 *
 * @author Vorozhtsov Mikhail
 */
@Component(
    modules = [
        RecruitmentScreenModule::class
    ]
)
interface RecruitmentScreenComponent : IRecruitmentApi {
    companion object {
        fun create(): IRecruitmentApi = DaggerRecruitmentScreenComponent.builder().build()
    }
}
