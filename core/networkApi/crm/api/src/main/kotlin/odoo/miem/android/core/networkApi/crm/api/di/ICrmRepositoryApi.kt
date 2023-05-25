package odoo.miem.android.core.networkApi.crm.api.di

import odoo.miem.android.core.di.api.Api
import odoo.miem.android.core.networkApi.crm.api.ICrmRepository

/**
 * [ICrmRepositoryApi] needed for wrapping over [ICrmRepository] and
 * providing in common **DI graph**
 *
 * @see Api
 *
 * @author Vorozhtsov Mikhail
 */
interface ICrmRepositoryApi : Api {

    val crmRepository: ICrmRepository
}
