package odoo.miem.android.feature.details.impl.di

import dagger.Component
import odoo.miem.android.feature.details.api.di.IDetailsScreenApi
import odoo.miem.android.feature.details.impl.DetailsScreen

/**
 * [DetailsScreenComponent] - **Dagger** component, which implements interface [IDetailsScreenApi]
 * Providing in general **DI graph** with a help of [DetailsScreenApiProvider].
 *
 * Included modules:
 *  - [DetailsScreenModule] - provide [DetailsScreen] in *DI graph*
 *
 * @author Vorozhtsov Mikhail
 */
@Component(
    modules = [
        DetailsScreenModule::class
    ]
)
interface DetailsScreenComponent : IDetailsScreenApi {
    companion object {
        fun create(): IDetailsScreenApi = DaggerDetailsScreenComponent.builder().build()
    }
}
