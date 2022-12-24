package odoo.miem.android.feature.authorization.base.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.feature.authorization.base.api.IAuthorizationScreen
import odoo.miem.android.feature.authorization.base.impl.AuthorizationScreen

/**
 * [AuthorizationScreenModule] - модуль для предоставления инстанса [AuthorizationScreen]
 * в общей di граф
 *
 * @author Ворожцов Михаил
 */
@Module
interface AuthorizationScreenModule {

    @Binds
    fun provideAuthorizationScreen(authorizationScreen: AuthorizationScreen): IAuthorizationScreen
}
