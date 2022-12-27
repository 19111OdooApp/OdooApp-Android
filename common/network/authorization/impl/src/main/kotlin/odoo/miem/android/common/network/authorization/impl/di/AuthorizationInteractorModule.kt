package odoo.miem.android.common.network.authorization.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.common.network.authorization.api.IAuthorizationInteractor
import odoo.miem.android.common.network.authorization.impl.AuthorizationInteractor

/**
 * [AuthorizationInteractorModule] - module for providing instance of [AuthorizationInteractor]
 * in **DI graph**
 *
 * @author Vorozhtsov Mikhail
 */
@Module
interface AuthorizationInteractorModule {

    @Binds
    fun provideAuthorizationUseCase(authorizationInteractor: AuthorizationInteractor): IAuthorizationInteractor
}
