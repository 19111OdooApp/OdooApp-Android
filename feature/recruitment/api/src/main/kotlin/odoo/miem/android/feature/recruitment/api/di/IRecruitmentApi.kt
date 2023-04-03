package odoo.miem.android.feature.recruitment.api.di

import odoo.miem.android.core.di.api.Api
import odoo.miem.android.feature.recruitment.api.IRecruitmentScreen

interface IRecruitmentApi : Api {

    val recruitmentScreen: IRecruitmentScreen
}
