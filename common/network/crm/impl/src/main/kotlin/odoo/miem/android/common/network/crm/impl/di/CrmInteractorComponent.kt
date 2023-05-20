package odoo.miem.android.common.network.crm.impl.di

import dagger.Component
import odoo.miem.android.common.network.crm.api.di.ICrmInteractorApi
import odoo.miem.android.common.network.crm.impl.CrmInteractor

/**
 * [CrmInteractorComponent] - **Dagger** component, which implements interface [ICrmInteractorApi]
 * Providing in general **DI graph** with a help of [RecruitmentInteractorApiProvider].
 *
 * Included modules:
 *  - [CrmInteractorModule] - provides [CrmInteractor] in *DI graph*
 *
 * @author Vorozhtsov Mikhail
 */
@Component(
    modules = [
        CrmInteractorModule::class
    ]
)
interface CrmInteractorComponent : ICrmInteractorApi {
    companion object {
        fun create(): ICrmInteractorApi = DaggerCrmInteractorComponent.builder().build()
    }
}
