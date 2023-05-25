package odoo.miem.android.core.networkApi.crm.impl.di

import dagger.Component
import odoo.miem.android.core.networkApi.crm.api.di.ICrmRepositoryApi
import odoo.miem.android.core.networkApi.crm.impl.CrmRepository

/**
 * [CrmRepositoryComponent] - **Dagger** component, which implements interface [ICrmRepositoryApi]
 * Providing in general **DI graph** with a help of [CrmRepositoryApiProvider].
 *
 * Included modules:
 *  - [CrmRepositoryModule] - provide [CrmRepository] in *DI graph*
 *
 * @author Vorozhtsov Mikhail
 */
@Component(
    modules = [
        CrmRepositoryModule::class
    ]
)
interface CrmRepositoryComponent : ICrmRepositoryApi {
    companion object {
        fun create(): ICrmRepositoryApi = DaggerCrmRepositoryComponent.builder()
            .build()
    }
}
