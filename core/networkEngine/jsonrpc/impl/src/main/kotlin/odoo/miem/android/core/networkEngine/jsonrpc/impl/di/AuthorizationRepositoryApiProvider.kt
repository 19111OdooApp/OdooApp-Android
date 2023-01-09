package odoo.miem.android.common.network.authorization.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.core.networkEngine.jsonrpc.api.di.IAuthorizationRepositoryApi
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider
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
