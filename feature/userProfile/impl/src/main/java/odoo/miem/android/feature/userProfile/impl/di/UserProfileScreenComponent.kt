package odoo.miem.android.feature.userProfile.impl.di

import dagger.Component
import odoo.miem.android.feature.userProfile.api.di.IUserProfileScreenApi
import odoo.miem.android.feature.userProfile.impl.UserProfileScreen

/**
 * [UserProfileScreenComponent] - **Dagger** component, which implements [IUserProfileScreenApi]
 * Providing in general **DI graph** with a help of [UserProfileScreenApiProvider].
 *
 * Included modules:
 *  - [UserProfileScreenModule] - provides [UserProfileScreen]
 *
 * @author Egor Danilov
 */
@Component(
    modules = [
        UserProfileScreenModule::class
    ]
)
interface UserProfileScreenComponent : IUserProfileScreenApi {
    companion object {
        fun create(): IUserProfileScreenApi = DaggerUserProfileScreenComponent.builder().build()
    }
}
