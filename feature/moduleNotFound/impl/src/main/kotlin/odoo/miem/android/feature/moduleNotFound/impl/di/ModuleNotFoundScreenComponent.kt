package odoo.miem.android.feature.moduleNotFound.impl.di

import dagger.Component
import odoo.miem.android.feature.moduleNotFound.api.di.IModuleNotFoundApi

/**
 * [ModuleNotFoundScreenComponent] - **Dagger** component, which implements interface [IModuleNotFoundApi]
 * It is provided to the general **DI graph** via [ModuleNotFoundScreenApiProvider].
 *
 * Connected modules:
 *  - [ModuleNotFoundScreenModule] - provides [ModuleNotFoundScreen] to the general *DI graph*
 *
 * @author Egor Danilov
 */
@Component(
    modules = [
        ModuleNotFoundScreenModule::class
    ],
)
interface ModuleNotFoundScreenComponent: IModuleNotFoundApi {
    companion object {
        fun create(): IModuleNotFoundApi = DaggerModuleNotFoundScreenComponent.builder().build()
    }
}