package odoo.miem.android.feature.crm.api.di

import odoo.miem.android.core.di.api.Api
import odoo.miem.android.feature.crm.api.ICrmScreen

interface ICrmApi : Api {

    val crmScreen: ICrmScreen
}
