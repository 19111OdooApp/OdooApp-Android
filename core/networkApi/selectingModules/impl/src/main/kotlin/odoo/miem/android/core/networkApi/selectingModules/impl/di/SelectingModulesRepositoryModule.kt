package odoo.miem.android.core.networkApi.selectingModules.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.core.networkApi.selectingModules.api.ISelectingModulesRepository
import odoo.miem.android.core.networkApi.selectingModules.impl.SelectingModulesRepository

/**
 * [SelectingModulesRepositoryModule] - module for providing instance of [SelectingModulesRepository]
 * in **DI graph**
 *
 * @author Egor Danilov
 */
@Module
interface SelectingModulesRepositoryModule {

    @Binds
    fun provideSelectingModulesRepository(impl: SelectingModulesRepository): ISelectingModulesRepository
}