package odoo.miem.android.common.network.selectingModules.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.common.network.selectingModules.api.ISelectingModulesInteractor
import odoo.miem.android.common.network.selectingModules.impl.SelectingModulesInteractor

/**
 * [SelectingModulesInteractorModule] - module for providing
 * instance of [SelectingModulesInteractor] in **DI graph**
 *
 * @author Egor Danilov
 */
@Module
interface SelectingModulesInteractorModule {

    @Binds
    fun provideSelectingModulesInteractor(impl: SelectingModulesInteractor): ISelectingModulesInteractor
}
