package odoo.miem.android.common.network.authorization.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.common.network.authorization.api.di.IAuthorizationInteractorApi
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider

/**
 * [AuthorizationInteractorApiProvider] - **Dagger** module for providing
 * [AuthorizationInteractorComponent] in general map
 *
 * @author Vorozhtsov Mikhail
 */
@Module
class AuthorizationInteractorApiProvider {

    @Provides
    @IntoMap
    @ApiKey(IAuthorizationInteractorApi::class)
    fun provideAuthorizationUseCaseApiProvider() = ApiProvider { AuthorizationInteractorComponent.create() }
}
