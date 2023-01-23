package odoo.miem.android.feature.authorization.base.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.feature.authorization.base.api.IAuthorizationScreen
import odoo.miem.android.feature.authorization.base.impl.AuthorizationScreen

/**
 * [AuthorizationScreenModule] - module for providing instance of [AuthorizationScreen]
 * to the general DI graph
 *
 * @author Vorozhtsov Mikhail
 */
@Module
interface AuthorizationScreenModule {

    @Binds
    fun provideAuthorizationScreen(authorizationScreen: AuthorizationScreen): IAuthorizationScreen
}
