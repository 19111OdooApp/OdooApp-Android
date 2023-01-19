package odoo.miem.android.common.network.selectingModules.impl.di

import dagger.Component
import odoo.miem.android.common.network.selectingModules.api.di.ISelectingModulesInteractorApi

/**
 * [SelectingModulesInteractor] - **Dagger** component, which implements interface [ISelectingModulesInteractorApi]
 * Providing in general **DI graph** with a help of [SelectingModulesInteractorApiProvider].
 *
 * Included modules:
 *  - [SelectingModulesInteractorModule] - provides [SelectingModulesInteractor] in *DI graph*
 *
 * @author Egor Danilov
 */
@Component(
    modules = [
        SelectingModulesInteractorModule::class
    ]
)
interface SelectingModulesInteractorComponent: ISelectingModulesInteractorApi {
    companion object {
        fun create(): ISelectingModulesInteractorApi =
            DaggerSelectingModulesInteractorComponent.builder().build()
    }
}