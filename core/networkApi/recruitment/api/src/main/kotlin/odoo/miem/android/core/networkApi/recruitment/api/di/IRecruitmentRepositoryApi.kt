package odoo.miem.android.core.networkApi.recruitment.api.di

import odoo.miem.android.core.di.api.Api
import odoo.miem.android.core.networkApi.recruitment.api.IRecruitmentRepository

/**
 * [IRecruitmentRepositoryApi] needed for wrapping over [IRecruitmentRepository] and
 * providing in common **DI graph**
 *
 * @see Api
 *
 * @author Vorozhtsov Mikhail
 */
interface IRecruitmentRepositoryApi : Api {

    val recruitmentRepository: IRecruitmentRepository
}
