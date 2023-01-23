package odoo.miem.android.feature.authorization.base.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider
import odoo.miem.android.feature.authorization.base.api.di.IAuthorizationApi

/**
 * [AuthorizationScreenApiProvider] - **Dagger** module for providing
 * [AuthorizationScreenComponent] to the general DI Map
 *
 * @author Vorozhtsov Mikhail
 */
@Module
class AuthorizationScreenApiProvider {

    @Provides
    @IntoMap
    @ApiKey(IAuthorizationApi::class)
    fun providesAuthorizationScreenApiProvider() = ApiProvider { AuthorizationScreenComponent.create() }
}
