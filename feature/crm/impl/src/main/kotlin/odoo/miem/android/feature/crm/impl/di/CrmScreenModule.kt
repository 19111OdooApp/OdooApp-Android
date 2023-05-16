package odoo.miem.android.feature.crm.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.feature.crm.api.ICrmKanbanScreen
import odoo.miem.android.feature.crm.impl.screen.kanban.CrmKanbanScreen

/**
 * [CrmScreenModule] - module for proving instance of [CrmKanbanScreen]
 * in general **DI graph**
 *
 * @author Alexander Lyutikov
 */
@Module
interface CrmScreenModule {

    @Binds
    fun provideCrmScreen(crmKanbanScreen: CrmKanbanScreen): ICrmKanbanScreen
}
