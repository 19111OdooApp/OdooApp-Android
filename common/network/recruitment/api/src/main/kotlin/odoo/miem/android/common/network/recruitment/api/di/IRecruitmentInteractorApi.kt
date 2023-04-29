package odoo.miem.android.common.network.recruitment.api.di

import odoo.miem.android.common.network.recruitment.api.IRecruitmentInteractor
import odoo.miem.android.core.di.api.Api

/**
 * [IRecruitmentInteractorApi] needed for wrapping over [IAuthorizationInteractor] and
 * providing in common **DI graph**
 *
 * @see Api
 *
 * @author Vorozhtsov Mikhail
 */
interface IRecruitmentInteractorApi : Api {

    val recruitmentInteractor: IRecruitmentInteractor
}
