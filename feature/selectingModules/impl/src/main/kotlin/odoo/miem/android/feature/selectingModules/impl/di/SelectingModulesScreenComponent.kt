package odoo.miem.android.feature.selectingModules.impl.di

import dagger.Component
import odoo.miem.android.feature.selectingModules.api.di.ISelectingModulesApi

/**
 * [SelectingModulesScreenComponent] - **Dagger** component, which implements [ISelectingModulesApi]
 * Providing in general **DI graph** with a help of [SelectingModulesScreenApiProvider].
 *
 * Included modules:
 *  - [SelectingModulesScreenModule] - provides [SelectingModulesScreen]
 *
 * @author Vorozhtsov Mikhail
 */
@Component(
    modules = [
        SelectingModulesScreenModule::class
    ]
)
interface SelectingModulesScreenComponent : ISelectingModulesApi {
    companion object {
        fun create(): ISelectingModulesApi = DaggerSelectingModulesScreenComponent.builder().build()
    }
}
