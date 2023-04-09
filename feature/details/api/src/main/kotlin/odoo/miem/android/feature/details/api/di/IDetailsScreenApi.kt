package odoo.miem.android.feature.details.api.di

import odoo.miem.android.core.di.api.Api
import odoo.miem.android.feature.details.api.IDetailsScreen

/**
 * [IDetailsScreenApi] needed for wrapping over [IDetailsScreen] and
 * providing in **DI graph**
 *
 * @see Api
 *
 * @author Vorozhtsov Mikhail
 */
interface IDetailsScreenApi : Api {

    val detailsScreen: IDetailsScreen
}
