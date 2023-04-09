package odoo.miem.android.feature.details.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider
import odoo.miem.android.feature.details.api.di.IDetailsScreenApi

/**
 * [DetailsScreenApiProvider] - **Dagger** module for providing
 * [DetailsScreenComponent] in DI map
 *
 * @author Vorozhtsov Mikhail
 */
@Module
class DetailsScreenApiProvider {

    @Provides
    @IntoMap
    @ApiKey(IDetailsScreenApi::class)
    fun providesDetailsScreenApiProvider() = ApiProvider { DetailsScreenComponent.create() }
}
