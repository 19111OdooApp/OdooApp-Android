package odoo.miem.android.feature.profile.impl.di

import dagger.Component
import odoo.miem.android.feature.profile.api.di.IProfileScreenApi
import odoo.miem.android.feature.profile.impl.ProfileScreen

/**
 * [ProfileScreenComponent] - **Dagger** component, which implements interface [IProfileScreenApi]
 * Providing in general **DI graph** with a help of [ProfileScreenApiProvider].
 *
 * Included modules:
 *  - [ProfileScreenModule] - provide [ProfileScreen] in *DI graph*
 *
 * @author Vorozhtsov Mikhail
 */
@Component(
    modules = [
        ProfileScreenModule::class
    ]
)
interface ProfileScreenComponent : IProfileScreenApi {
    companion object {
        fun create(): IProfileScreenApi = DaggerProfileScreenComponent.builder().build()
    }
}