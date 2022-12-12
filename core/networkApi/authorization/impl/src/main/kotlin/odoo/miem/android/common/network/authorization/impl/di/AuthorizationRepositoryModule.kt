package odoo.miem.android.common.network.authorization.impl.di

import dagger.Module
import dagger.Provides
import odoo.miem.android.common.network.authorization.api.IAuthorizationRepository
import odoo.miem.android.common.network.authorization.impl.AuthorizationRepository

/**
 * [AuthorizationRepositoryModule] - module for providing instance of [AuthorizationRepository]
 * in **DI graph**
 *
 * @author Vorozhtsov Mikhail
 */
@Module
class AuthorizationRepositoryModule {

    @Provides
    fun provideAuthorizationRepository(): IAuthorizationRepository = AuthorizationRepository()
}
