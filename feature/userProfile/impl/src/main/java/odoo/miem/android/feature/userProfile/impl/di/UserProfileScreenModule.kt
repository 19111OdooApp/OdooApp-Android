package odoo.miem.android.feature.userProfile.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.feature.userProfile.api.IUserProfileScreen
import odoo.miem.android.feature.userProfile.impl.UserProfileScreen

/**
 * [UserProfileScreenModule] - module for proving instance of [UserProfileScreen]
 * in general **DI graph**
 *
 * @author Egor Danilov
 */
@Module
interface UserProfileScreenModule {

    @Binds
    fun provideUserProfileScreen(impl: UserProfileScreen): IUserProfileScreen
}
