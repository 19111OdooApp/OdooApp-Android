package odoo.miem.android.core.networkApi.userInfo.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider
import odoo.miem.android.core.networkApi.userInfo.api.di.IUserInfoRepositoryApi

/**
 * [UserInfoRepositoryApiProvider] - **Dagger** module for providing
 * [UserInfoRepositoryComponent] in general map
 *
 * @author Egor Danilov
 */
@Module
class UserInfoRepositoryApiProvider {

    @Provides
    @IntoMap
    @ApiKey(IUserInfoRepositoryApi::class)
    fun provideUserInfoRepositoryApiProvider() = ApiProvider {
        UserInfoRepositoryComponent.create()
    }
}