package odoo.miem.android.feature.crm.impl.di

import dagger.Component
import odoo.miem.android.feature.crm.api.di.ICrmApi

/**
 * [CrmScreenComponent] - **Dagger** component, which implements [ICrmApi]
 * Providing in general **DI graph** with a help of [CrmScreenApiProvider].
 *
 * Included modules:
 *  - [CrmScreenModule] - provides [CrmScreen]
 *
 * @author Alexander Lyutikov
 */
@Component(
    modules = [
        CrmScreenModule::class
    ]
)
interface CrmScreenComponent : ICrmApi {
    companion object {
        fun create(): ICrmApi = DaggerCrmScreenComponent.builder().build()
    }
}
