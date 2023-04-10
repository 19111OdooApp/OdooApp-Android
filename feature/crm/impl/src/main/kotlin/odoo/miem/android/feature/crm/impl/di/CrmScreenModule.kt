package odoo.miem.android.feature.crm.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.feature.crm.api.ICrmScreen
import odoo.miem.android.feature.crm.impl.crmScreen.CrmScreen

/**
 * [CrmScreenModule] - module for proving instance of [CrmScreen]
 * in general **DI graph**
 *
 * @author Alexander Lyutikov
 */
@Module
interface CrmScreenModule {

    @Binds
    fun provideCrmScreen(crmScreen: CrmScreen): ICrmScreen
}
