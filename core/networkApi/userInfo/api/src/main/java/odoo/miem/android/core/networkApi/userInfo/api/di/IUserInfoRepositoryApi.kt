package odoo.miem.android.core.networkApi.userInfo.api.di

import odoo.miem.android.core.di.api.Api
import odoo.miem.android.core.networkApi.userInfo.api.IUserInfoRepository

/**
 * [IUserInfoRepositoryApi] needed for wrapping over [IUserInfoRepository] and
 * providing in common **DI graph**
 *
 * @see Api
 *
 * @author Egor Danilov
 */
interface IUserInfoRepositoryApi : Api {

    val userInfoRepository: IUserInfoRepository
}
