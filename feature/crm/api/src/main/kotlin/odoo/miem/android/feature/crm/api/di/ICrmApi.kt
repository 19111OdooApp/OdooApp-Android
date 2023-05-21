package odoo.miem.android.feature.crm.api.di

import odoo.miem.android.core.di.api.Api
import odoo.miem.android.feature.crm.api.ICrmDetailsScreen
import odoo.miem.android.feature.crm.api.ICrmKanbanScreen

interface ICrmApi : Api {

    val crmKanbanScreen: ICrmKanbanScreen

    val crmDetailsScreen: ICrmDetailsScreen
}
