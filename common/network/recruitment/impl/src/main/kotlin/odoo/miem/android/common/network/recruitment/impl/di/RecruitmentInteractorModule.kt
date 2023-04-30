package odoo.miem.android.common.network.recruitment.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.common.network.recruitment.api.IRecruitmentInteractor
import odoo.miem.android.common.network.recruitment.impl.RecruitmentInteractor

/**
 * [RecruitmentInteractorModule] - module for providing instance of [RecruitmentInteractor]
 * in **DI graph**
 *
 * @author Vorozhtsov Mikhail
 */
@Module
interface RecruitmentInteractorModule {

    @Binds
    fun provideRecruitmentUseCase(recruitmentInteractor: RecruitmentInteractor): IRecruitmentInteractor
}
