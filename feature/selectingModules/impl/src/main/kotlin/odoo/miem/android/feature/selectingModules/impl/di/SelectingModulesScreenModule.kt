package odoo.miem.android.feature.selectingModules.impl.di

import dagger.Module
import dagger.Provides
import odoo.miem.android.feature.selectingModules.api.ISelectingModulesScreen
import odoo.miem.android.feature.selectingModules.impl.SelectingModulesScreen

/**
 * [SelectingModulesScreenModule] - module for proving instance of [SelectingModulesScreen]
 * in general **DI graph**
 *
 * @author Vorozhtsov Mikhail
 */
@Module
class SelectingModulesScreenModule {

    @Provides
    fun provideSelectingModulesScreen(): ISelectingModulesScreen = SelectingModulesScreen()
}
