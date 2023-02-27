package odoo.miem.android.core.networkApi.userInfo.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.core.networkApi.userInfo.api.IUserInfoRepository
import odoo.miem.android.core.networkApi.userInfo.impl.UserInfoRepository

/**
 * [UserInfoRepositoryModule] - module for providing instance of [UserInfoRepository]
 * in **DI graph**
 *
 * @author Egor Danilov
 */
@Module
interface UserInfoRepositoryModule {

    @Binds
    fun provideSelectingModulesRepository(impl: UserInfoRepository): IUserInfoRepository
}
