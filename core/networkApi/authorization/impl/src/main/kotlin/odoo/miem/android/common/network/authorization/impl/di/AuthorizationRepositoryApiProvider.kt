package odoo.miem.android.common.network.authorization.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.common.network.authorization.api.di.IAuthorizationRepositoryApi
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider

// TODO Description
/**
 * [AuthorizationRepositoryApiProvider] - **Dagger** модуль для предоставления
 * [AuthorizationRepositoryComponent] в общую мапу
 *
 * @author Ворожцов Михаил
 */
@Module
class AuthorizationRepositoryApiProvider {

    @Provides
    @IntoMap
    @ApiKey(IAuthorizationRepositoryApi::class)
    fun provideAuthorizationRepositoryApiProvider() = ApiProvider { AuthorizationRepositoryComponent.create() }
}
