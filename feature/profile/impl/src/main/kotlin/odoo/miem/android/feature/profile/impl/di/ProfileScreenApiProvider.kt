package odoo.miem.android.feature.profile.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider
import odoo.miem.android.feature.profile.api.di.IProfileScreenApi

/**
 * [ProfileScreenApiProvider] - **Dagger** module for providing
 * [ProfileScreenComponent] in DI map
 *
 * @author Vorozhtsov Mikhail
 */
@Module
class ProfileScreenApiProvider {

    @Provides
    @IntoMap
    @ApiKey(IProfileScreenApi::class)
    fun providesProfileScreenApiProvider() = ApiProvider { ProfileScreenComponent.create() }
}
