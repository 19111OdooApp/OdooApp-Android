package odoo.miem.android.core.networkApi.userModules.impl.di

import dagger.Component
import odoo.miem.android.core.networkApi.userInfo.api.di.IUserModulesRepositoryApi
import odoo.miem.android.core.networkApi.userModules.impl.di.UserModulesRepositoryModule

/**
 * [UserModulesRepositoryModule] - **Dagger** component, which implements interface [IUserModulesRepositoryApi]
 * Providing in general **DI graph** with a help of [UserModulesRepositoryApiProvider].
 *
 * Included modules:
 *  - [UserModulesRepositoryModule] - provides [UserModulesRepository] in *DI graph*
 *
 * @author Egor Danilov
 */
@Component(
    modules = [
        UserModulesRepositoryModule::class
    ]
)
interface UserModulesRepositoryComponent : IUserModulesRepositoryApi {
    companion object {
        fun create(): IUserModulesRepositoryApi = DaggerUserModulesRepositoryComponent.builder()
            .build()
    }
}
