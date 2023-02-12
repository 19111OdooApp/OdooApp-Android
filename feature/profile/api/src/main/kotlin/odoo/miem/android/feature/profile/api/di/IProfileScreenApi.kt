package odoo.miem.android.feature.profile.api.di

import odoo.miem.android.core.di.api.Api
import odoo.miem.android.feature.profile.api.IProfileScreen

/**
 * [IProfileScreenApi] needed for wrapping over [IProfileScreen] and
 * providing in **DI graph**
 *
 * @see Api
 *
 * @author Vorozhtsov Mikhail
 */
interface IProfileScreenApi : Api {

    val profileScreen: IProfileScreen
}