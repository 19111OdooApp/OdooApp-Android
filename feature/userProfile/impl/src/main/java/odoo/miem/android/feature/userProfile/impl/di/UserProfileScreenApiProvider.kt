package odoo.miem.android.feature.userProfile.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider
import odoo.miem.android.feature.userProfile.api.di.IUserProfileScreenApi

/**
 * [UserProfileScreenApiProvider] - **Dagger** module for providing
 * [UserProfileScreenComponent] in general map
 *
 * @author Egor Danilov
 */
@Module
class UserProfileScreenApiProvider {

    @Provides
    @IntoMap
    @ApiKey(IUserProfileScreenApi::class)
    fun provideUserProfileScreenApiProvider() = ApiProvider { UserProfileScreenComponent.create() }
}
