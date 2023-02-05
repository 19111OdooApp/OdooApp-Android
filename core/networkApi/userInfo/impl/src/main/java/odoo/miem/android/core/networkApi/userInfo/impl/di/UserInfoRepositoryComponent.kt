package odoo.miem.android.core.networkApi.userInfo.impl.di

import dagger.Component
import odoo.miem.android.core.networkApi.userInfo.api.di.IUserInfoRepositoryApi

/**
 * [UserInfoRepositoryModule] - **Dagger** component, which implements interface [IUserInfoRepositoryApi]
 * Providing in general **DI graph** with a help of [UserInfoRepositoryApiProvider].
 *
 * Included modules:
 *  - [UserInfoRepositoryModule] - provides [UserInfoRepository] in *DI graph*
 *
 * @author Egor Danilov
 */
@Component(
    modules = [
        UserInfoRepositoryModule::class
    ]
)
interface UserInfoRepositoryComponent : IUserInfoRepositoryApi {
    companion object {
        fun create(): IUserInfoRepositoryApi = DaggerUserInfoRepositoryComponent.builder()
            .build()
    }
}
