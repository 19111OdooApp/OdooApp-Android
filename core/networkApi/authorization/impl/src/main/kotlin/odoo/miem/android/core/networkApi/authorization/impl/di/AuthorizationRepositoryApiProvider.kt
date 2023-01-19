package odoo.miem.android.core.networkApi.authorization.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider
import odoo.miem.android.core.networkApi.authorization.api.di.IAuthorizationRepositoryApi
/**
 * [AuthorizationRepositoryApiProvider] - **Dagger** module for providing
 * [AuthorizationRepositoryComponent] in general map
 *
 * @author Vorozhtso Mikhail
 */
@Module
class AuthorizationRepositoryApiProvider {

    @Provides
    @IntoMap
    @ApiKey(IAuthorizationRepositoryApi::class)
    fun provideAuthorizationRepositoryApiProvider() = ApiProvider { AuthorizationRepositoryComponent.create() }
}
