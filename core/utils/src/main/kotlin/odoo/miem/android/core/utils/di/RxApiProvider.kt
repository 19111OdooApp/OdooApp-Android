package odoo.miem.android.core.utils.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider

@Module
class RxApiProvider {

    @Provides
    @IntoMap
    @ApiKey(RxApi::class)
    fun providesLoggerFactoryApiProvider() = ApiProvider {
        RxComponent.create()
    }
}
