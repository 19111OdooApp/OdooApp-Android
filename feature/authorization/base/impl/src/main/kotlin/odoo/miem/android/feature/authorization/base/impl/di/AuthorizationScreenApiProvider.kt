package odoo.miem.android.feature.authorization.base.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider
import odoo.miem.android.feature.authorization.base.api.di.IAuthorizationApi

/**
 * [AuthorizationScreenApiProvider] - **Dagger** модуль для предоставления
 * [AuthorizationScreenComponent] в общую мапу
 *
 * @author Ворожцов Михаил
 */
@Module
class AuthorizationScreenApiProvider {

    @Provides
    @IntoMap
    @ApiKey(IAuthorizationApi::class)
    fun providesLoggerFactoryApiProvider() = ApiProvider { AuthorizationScreenComponent.create() }
}