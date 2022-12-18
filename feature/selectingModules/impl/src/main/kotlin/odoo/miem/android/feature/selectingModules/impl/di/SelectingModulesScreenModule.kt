package odoo.miem.android.feature.selectingModules.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.feature.selectingModules.api.ISelectingModulesScreen
import odoo.miem.android.feature.selectingModules.impl.SelectingModulesScreen

/**
 * [SelectingModulesScreenModule] - module for proving instance of [SelectingModulesScreen]
 * in general **DI graph**
 *
 * @author Vorozhtsov Mikhail
 */
@Module
interface SelectingModulesScreenModule {

    @Binds
    fun provideSelectingModulesScreen(selectingModulesScreen: SelectingModulesScreen): ISelectingModulesScreen
}
