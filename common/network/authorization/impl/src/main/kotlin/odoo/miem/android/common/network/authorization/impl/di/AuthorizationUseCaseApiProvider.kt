package odoo.miem.android.common.network.authorization.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.common.network.authorization.api.di.IAuthorizationUseCaseApi
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider

// TODO Description
/**
 * [AuthorizationUseCaseApiProvider] - **Dagger** модуль для предоставления
 * [AuthorizationUseCaseComponent] в общую мапу
 *
 * @author Ворожцов Михаил
 */
@Module
class AuthorizationUseCaseApiProvider {

    @Provides
    @IntoMap
    @ApiKey(IAuthorizationUseCaseApi::class)
    fun provideAuthorizationUseCaseApiProvider() = ApiProvider { AuthorizationUseCaseComponent.create() }
}
