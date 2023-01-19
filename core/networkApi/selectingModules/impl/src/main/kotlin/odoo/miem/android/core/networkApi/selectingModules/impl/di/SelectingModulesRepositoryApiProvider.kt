package odoo.miem.android.core.networkApi.selectingModules.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider
import odoo.miem.android.core.networkApi.selectingModules.api.di.ISelectingModulesRepositoryApi

/**
 * [SelectingModulesRepositoryApiProvider] - **Dagger** module for providing
 * [SelectingModulesRepositoryComponent] in general map
 *
 * @author Egor Danilov
 */
@Module
class SelectingModulesRepositoryApiProvider {

    @Provides
    @IntoMap
    @ApiKey(ISelectingModulesRepositoryApi::class)
    fun provideAuthorizationRepositoryApiProvider() = ApiProvider { SelectingModulesRepositoryComponent.create() }
}