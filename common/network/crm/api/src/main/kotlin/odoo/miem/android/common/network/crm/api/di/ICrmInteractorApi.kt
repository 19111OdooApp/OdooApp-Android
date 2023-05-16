package odoo.miem.android.common.network.crm.api.di

import odoo.miem.android.common.network.crm.api.ICrmDetailsInteractor
import odoo.miem.android.common.network.crm.api.ICrmInteractor
import odoo.miem.android.core.di.api.Api

/**
 * [ICrmInteractorApi] needed for wrapping over [ICrmInteractor], [ICrmDetailsInteractor] and
 * providing in common **DI graph**
 *
 * @see Api
 *
 * @author Vorozhtsov Mikhail
 */
interface ICrmInteractorApi : Api {

    val сrmInteractor: ICrmInteractor

    val сrmDetailsInteractor: ICrmDetailsInteractor
}
