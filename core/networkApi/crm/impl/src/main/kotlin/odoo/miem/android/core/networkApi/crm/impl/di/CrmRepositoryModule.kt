package odoo.miem.android.core.networkApi.crm.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.core.networkApi.crm.api.ICrmRepository
import odoo.miem.android.core.networkApi.crm.impl.CrmRepository

/**
 * [CrmRepositoryModule] - module for providing instance of [CrmRepository]
 * in **DI graph**
 *
 * @author Vorozhtsov Mikhail
 */
@Module
interface CrmRepositoryModule {

    @Binds
    fun provideRecruitmentRepository(impl: CrmRepository): ICrmRepository
}
