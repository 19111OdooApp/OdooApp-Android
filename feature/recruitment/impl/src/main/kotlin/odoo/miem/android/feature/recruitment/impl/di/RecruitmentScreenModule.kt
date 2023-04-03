package odoo.miem.android.feature.recruitment.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.feature.recruitment.api.IRecruitmentScreen
import odoo.miem.android.feature.recruitment.impl.recruitmentScreen.RecruitmentScreen

/**
 * [RecruitmentScreenModule] - module for proving instance of [RecruitmentScreen]
 * in general **DI graph**
 *
 * @author Alexander Lyutikov
 */
@Module
interface RecruitmentScreenModule {

    @Binds
    fun provideRecruitmentScreen(recruitmentScreen: RecruitmentScreen): IRecruitmentScreen
}
