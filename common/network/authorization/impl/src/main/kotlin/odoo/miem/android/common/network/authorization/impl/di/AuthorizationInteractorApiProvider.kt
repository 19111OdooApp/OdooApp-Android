package odoo.miem.android.common.network.authorization.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.common.network.authorization.api.di.IAuthorizationInteractorApi
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider

// TODO Description
/**
 * [AuthorizationInteractorApiProvider] - **Dagger** модуль для предоставления
 * [AuthorizationInteractorComponent] в общую мапу
 *
 * @author Ворожцов Михаил
 */
@Module
class AuthorizationInteractorApiProvider {

    @Provides
    @IntoMap
    @ApiKey(IAuthorizationInteractorApi::class)
    fun provideAuthorizationUseCaseApiProvider() = ApiProvider { AuthorizationInteractorComponent.create() }
}
