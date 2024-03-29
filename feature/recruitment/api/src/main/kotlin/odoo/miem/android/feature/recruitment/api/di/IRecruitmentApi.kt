package odoo.miem.android.feature.recruitment.api.di

import odoo.miem.android.core.di.api.Api
import odoo.miem.android.feature.recruitment.api.IRecruitmentDetailsScreen
import odoo.miem.android.feature.recruitment.api.IRecruitmentJobsScreen
import odoo.miem.android.feature.recruitment.api.IRecruitmentKanbanScreen

/**
 * [IRecruitmentApi] is api for recruitment in general DI graph
 *
 * @author Vorozhtcov Mikhail
 */
interface IRecruitmentApi : Api {

    val recruitmentScreen: IRecruitmentKanbanScreen

    val recruitmentDetailsScreen: IRecruitmentDetailsScreen

    val recruitmentJobsScreen: IRecruitmentJobsScreen
}
