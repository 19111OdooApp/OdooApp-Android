package odoo.miem.android.feature.selectingModules.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider
import odoo.miem.android.feature.selectingModules.api.di.ISelectingModulesApi

/**
 * [SelectingModulesScreenApiProvider] - **Dagger** module for providing
 * [SelectingModulesScreenComponent] in general map
 *
 * @author Vorozhtsov Mikhail
 */
@Module
class SelectingModulesScreenApiProvider {

    @Provides
    @IntoMap
    @ApiKey(ISelectingModulesApi::class)
    fun providesSelectingModulesScreenApiProvider() = ApiProvider { SelectingModulesScreenComponent.create() }
}
