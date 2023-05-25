package odoo.miem.android.common.network.recruitment.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.common.network.recruitment.api.IRecruitmentDetailsInteractor
import odoo.miem.android.common.network.recruitment.api.IRecruitmentInteractor
import odoo.miem.android.common.network.recruitment.api.IRecruitmentJobsInteractor
import odoo.miem.android.common.network.recruitment.impl.RecruitmentDetailsInteractor
import odoo.miem.android.common.network.recruitment.impl.RecruitmentInteractor
import odoo.miem.android.common.network.recruitment.impl.RecruitmentJobsInteractor

/**
 * [RecruitmentInteractorModule] - module for providing instance of [RecruitmentInteractor]
 * in **DI graph**
 *
 * @author Vorozhtsov Mikhail
 */
@Module
interface RecruitmentInteractorModule {

    @Binds
    fun provideRecruitmentUseCase(impl: RecruitmentInteractor): IRecruitmentInteractor

    @Binds
    fun provideRecruitmentJobsUseCase(impl: RecruitmentJobsInteractor): IRecruitmentJobsInteractor

    @Binds
    fun provideRecruitmentDetailsUseCase(impl: RecruitmentDetailsInteractor): IRecruitmentDetailsInteractor
}
