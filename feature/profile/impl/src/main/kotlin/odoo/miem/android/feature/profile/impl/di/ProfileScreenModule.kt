package odoo.miem.android.feature.profile.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.feature.profile.api.IProfileScreen
import odoo.miem.android.feature.profile.impl.ProfileScreen

/**
 * [ProfileScreenModule] - module for providing instance of [ProfileScreen]
 * in **DI graph**
 *
 * @author Vorozhtsov Mikhail
 */
@Module
interface ProfileScreenModule {

    @Binds
    fun provideProfileScreen(profileScreen: ProfileScreen): IProfileScreen
}