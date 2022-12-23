package odoo.miem.android.feature.authorization.base.impl.di

import dagger.Module
import dagger.Provides
import odoo.miem.android.core.utils.rx.PresentationSchedulers
import odoo.miem.android.feature.authorization.base.api.IAuthorizationScreen
import odoo.miem.android.feature.authorization.base.impl.AuthorizationScreen
import odoo.miem.android.feature.authorization.base.impl.AuthorizationViewModel

/**
 * [AuthorizationScreenModule] - модуль для предоставления инстанса [AuthorizationScreen]
 * в общей di граф
 *
 * @author Ворожцов Михаил
 */
@Module
class AuthorizationScreenModule {

    @Provides
    fun provideAuthorizationScreen(): IAuthorizationScreen = AuthorizationScreen()

    @Provides
    @AuthorizationScreenScope
    fun providesViewModel(schedulers: PresentationSchedulers): AuthorizationViewModel =
        AuthorizationViewModel(schedulers)
}
