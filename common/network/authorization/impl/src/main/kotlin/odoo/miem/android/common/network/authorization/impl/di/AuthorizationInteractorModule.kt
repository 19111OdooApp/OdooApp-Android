package odoo.miem.android.common.network.authorization.impl.di

import dagger.Module
import dagger.Provides
import odoo.miem.android.common.network.authorization.api.IAuthorizationInteractor
import odoo.miem.android.common.network.authorization.impl.AuthorizationInteractor

/**
 * [AuthorizationInteractorModule] - module for providing instance of [AuthorizationInteractor]
 * in **DI graph**
 *
 * @author Vorozhtsov Mikhail
 */
@Module
class AuthorizationInteractorModule {

    @Provides
    fun provideAuthorizationUseCase(): IAuthorizationInteractor = AuthorizationInteractor()
}
