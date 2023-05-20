package odoo.miem.android.common.network.crm.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.common.network.crm.api.ICrmDetailsInteractor
import odoo.miem.android.common.network.crm.api.ICrmInteractor
import odoo.miem.android.common.network.crm.impl.CrmDetailsInteractor
import odoo.miem.android.common.network.crm.impl.CrmInteractor

/**
 * [CrmInteractorModule] - module for providing instance of [CrmInteractor]
 * in **DI graph**
 *
 * @author Vorozhtsov Mikhail
 */
@Module
interface CrmInteractorModule {

    @Binds
    fun provideRecruitmentUseCase(impl: CrmInteractor): ICrmInteractor

    @Binds
    fun provideRecruitmentDetailsUseCase(impl: CrmDetailsInteractor): ICrmDetailsInteractor
}
