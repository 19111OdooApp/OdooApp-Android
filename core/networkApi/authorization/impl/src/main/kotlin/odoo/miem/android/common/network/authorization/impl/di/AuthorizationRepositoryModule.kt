package odoo.miem.android.common.network.authorization.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.common.network.authorization.api.IAuthorizationRepository
import odoo.miem.android.common.network.authorization.impl.AuthorizationRepository

/**
 * [AuthorizationRepositoryModule] - module for providing instance of [AuthorizationRepository]
 * in **DI graph**
 *
 * @author Vorozhtsov Mikhail
 */
@Module
interface AuthorizationRepositoryModule {

    @Binds
    fun provideAuthorizationRepository(authorizationRepository: AuthorizationRepository): IAuthorizationRepository
}
