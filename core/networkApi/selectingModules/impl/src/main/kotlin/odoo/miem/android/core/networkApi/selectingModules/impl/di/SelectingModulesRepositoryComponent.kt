package odoo.miem.android.core.networkApi.selectingModules.impl.di

import dagger.Component
import odoo.miem.android.core.networkApi.selectingModules.api.di.ISelectingModulesRepositoryApi

/**
 * [SelectingModulesRepositoryModule] - **Dagger** component, which implements interface [ISelectingModulesRepositoryApi]
 * Providing in general **DI graph** with a help of [AuthorizationRepositoryApiProvider].
 *
 * Included modules:
 *  - [SelectingModulesRepositoryModule] - provides [SelectingModulesRepository] in *DI graph*
 *
 * @author Egor Danilov
 */
@Component(
    modules = [
        SelectingModulesRepositoryModule::class
    ]
)
interface SelectingModulesRepositoryComponent : ISelectingModulesRepositoryApi {
    companion object {
        fun create(): ISelectingModulesRepositoryApi = DaggerSelectingModulesRepositoryComponent.builder()
            .build()
    }
}
