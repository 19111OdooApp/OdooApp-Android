package odoo.miem.android.feature.moduleNotFound.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider
import odoo.miem.android.feature.moduleNotFound.api.di.IModuleNotFoundApi

/**
 * [ModuleNotFoundScreenApiProvider] - **Dagger** module for providing
 * [ModuleNotFoundScreenComponent] to the general DI map
 *
 * @author Egor Danilov
 */
@Module
class ModuleNotFoundScreenApiProvider {

    @Provides
    @IntoMap
    @ApiKey(IModuleNotFoundApi::class)
    fun providesModuleNotFoundScreenApiProvider() = ApiProvider {
        ModuleNotFoundScreenComponent.create()
    }
}