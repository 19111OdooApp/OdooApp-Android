package odoo.miem.android.feature.userProfile.api.di

import odoo.miem.android.core.di.api.Api
import odoo.miem.android.feature.userProfile.api.IUserProfileScreen

/**
 * [IUserProfileScreenApi] needs for wrapping [IUserProfileScreen] for
 * subsequent submission to the DI graph
 *
 * @see Api
 *
 * @author Egor Danilov
 */
interface IUserProfileScreenApi : Api {

    val userProfileScreen: IUserProfileScreen
}
