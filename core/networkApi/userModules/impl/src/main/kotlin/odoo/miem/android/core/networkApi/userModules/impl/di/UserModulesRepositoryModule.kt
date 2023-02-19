package odoo.miem.android.core.networkApi.userModules.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.core.networkApi.userInfo.api.IUserModulesRepository
import odoo.miem.android.core.networkApi.userModules.impl.UserModulesRepository

/**
 * [UserModulesRepositoryModule] - module for providing instance of [UserModulesRepository]
 * in **DI graph**
 *
 * @author Egor Danilov
 */
@Module
interface UserModulesRepositoryModule {

    @Binds
    fun provideUserModulesRepository(impl: UserModulesRepository): IUserModulesRepository
}
