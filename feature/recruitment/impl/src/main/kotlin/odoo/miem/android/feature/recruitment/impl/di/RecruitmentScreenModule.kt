package odoo.miem.android.feature.recruitment.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.feature.recruitment.api.IRecruitmentDetailsScreen
import odoo.miem.android.feature.recruitment.api.IRecruitmentJobsScreen
import odoo.miem.android.feature.recruitment.api.IRecruitmentKanbanScreen
import odoo.miem.android.feature.recruitment.impl.screen.details.RecruitmentDetailsScreen
import odoo.miem.android.feature.recruitment.impl.screen.jobs.RecruitmentJobsScreen
import odoo.miem.android.feature.recruitment.impl.screen.kanban.RecruitmentKanbanScreen

/**
 * [RecruitmentScreenModule] - module for proving instance of [RecruitmentKanbanScreen],
 * [RecruitmentDetailsScreen] and [RecruitmentJobsScreen] in general **DI graph**
 *
 * @author Alexander Lyutikov
 */
@Module
interface RecruitmentScreenModule {

    @Binds
    fun provideRecruitmentScreen(recruitmentScreen: RecruitmentKanbanScreen): IRecruitmentKanbanScreen

    @Binds
    fun provideRecruitmentDetailsScreen(recruitmentDetailsScreen: RecruitmentDetailsScreen): IRecruitmentDetailsScreen

    @Binds
    fun provideRecruitmentJobsScreen(recruitmentJobsScreen: RecruitmentJobsScreen): IRecruitmentJobsScreen
}
