package odoo.miem.android.common.network.authorization.impl.di

import dagger.Module
import dagger.Provides
import odoo.miem.android.common.network.authorization.api.IAuthorizationUseCase
import odoo.miem.android.common.network.authorization.impl.AuthorizationUseCase

// TODO Description
/**
 * [AuthorizationUseCaseModule] - модуль для предоставления инстанса [AuthorizationScreen]
 * в общей di граф
 *
 * @author Ворожцов Михаил
 */
@Module
class AuthorizationUseCaseModule {

    @Provides
    fun provideAuthorizationUseCase(): IAuthorizationUseCase = AuthorizationUseCase()
}
