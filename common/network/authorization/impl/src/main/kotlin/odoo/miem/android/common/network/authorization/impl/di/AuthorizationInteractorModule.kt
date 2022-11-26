package odoo.miem.android.common.network.authorization.impl.di

import dagger.Module
import dagger.Provides
import odoo.miem.android.common.network.authorization.api.IAuthorizationInteractor
import odoo.miem.android.common.network.authorization.impl.AuthorizationInteractor

// TODO Description
/**
 * [AuthorizationInteractorModule] - модуль для предоставления инстанса [AuthorizationScreen]
 * в общей di граф
 *
 * @author Ворожцов Михаил
 */
@Module
class AuthorizationInteractorModule {

    @Provides
    fun provideAuthorizationUseCase(): IAuthorizationInteractor = AuthorizationInteractor()
}
