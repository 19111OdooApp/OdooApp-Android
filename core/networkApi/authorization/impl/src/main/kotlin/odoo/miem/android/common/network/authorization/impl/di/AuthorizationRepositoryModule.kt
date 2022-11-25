package odoo.miem.android.common.network.authorization.impl.di

import dagger.Module
import dagger.Provides
import odoo.miem.android.common.network.authorization.api.IAuthorizationRepository
import odoo.miem.android.common.network.authorization.impl.AuthorizationRepository

// TODO Description
/**
 * [AuthorizationRepositoryModule] - модуль для предоставления инстанса [AuthorizationScreen]
 * в общей di граф
 *
 * @author Ворожцов Михаил
 */
@Module
class AuthorizationRepositoryModule {

    @Provides
    fun provideAuthorizationRepository(): IAuthorizationRepository = AuthorizationRepository()
}
