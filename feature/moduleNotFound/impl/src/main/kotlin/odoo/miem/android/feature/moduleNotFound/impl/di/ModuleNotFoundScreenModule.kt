package odoo.miem.android.feature.moduleNotFound.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.feature.moduleNotFound.api.IModuleNotFoundScreen
import odoo.miem.android.feature.moduleNotFound.impl.ModuleNotFoundScreen

/**
 * [ModuleNotFoundScreenModule] - module for providing instance of [ModuleNotFoundScreen]
 * to the general DI graph
 *
 * @author Egor Danilov
 */
@Module
interface ModuleNotFoundScreenModule {

    @Binds
    fun provideModuleNotFoundScreen(moduleNotFoundScreen: ModuleNotFoundScreen): IModuleNotFoundScreen
}
