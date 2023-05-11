package odoo.miem.android.core.networkApi.recruitment.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.core.networkApi.recruitment.api.IRecruitmentRepository
import odoo.miem.android.core.networkApi.recruitment.impl.RecruitmentRepository

/**
 * [RecruitmentRepositoryModule] - module for providing instance of [RecruitmentRepository]
 * in **DI graph**
 *
 * @author Vorozhtsov Mikhail
 */
@Module
interface RecruitmentRepositoryModule {

    @Binds
    fun provideRecruitmentRepository(recruitmentRepository: RecruitmentRepository): IRecruitmentRepository
}
