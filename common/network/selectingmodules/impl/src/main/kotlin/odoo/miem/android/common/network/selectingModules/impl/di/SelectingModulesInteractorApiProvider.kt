package odoo.miem.android.common.network.selectingModules.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.common.network.selectingModules.api.di.ISelectingModulesInteractorApi
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider

/**
 * [SelectingModulesInteractorApiProvider] - **Dagger** module for providing
 * [SelectingModulesInteractorComponent] in general map
 *
 * @author Vorozhtso Mikhail
 */
@Module
class SelectingModulesInteractorApiProvider {

    @Provides
    @IntoMap
    @ApiKey(ISelectingModulesInteractorApi::class)
    fun provideSelectingModulesUseCaseApiProvider() = ApiProvider { SelectingModulesInteractorComponent.create() }
}