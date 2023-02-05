package odoo.miem.android.core.networkApi.userModules.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider
import odoo.miem.android.core.networkApi.userInfo.api.di.IUserModulesRepositoryApi

/**
 * [UserModulesRepositoryApiProvider] - **Dagger** module for providing
 * [UserModulesRepositoryComponent] in general map
 *
 * @author Egor Danilov
 */
@Module
class UserModulesRepositoryApiProvider {

    @Provides
    @IntoMap
    @ApiKey(IUserModulesRepositoryApi::class)
    fun provideUserModulesRepositoryApiProvider() = ApiProvider {
        UserModulesRepositoryComponent.create()
    }
}