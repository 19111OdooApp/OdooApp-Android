package odoo.miem.android.feature.crm.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider
import odoo.miem.android.feature.crm.api.di.ICrmApi

/**
 * [CrmScreenApiProvider] - **Dagger** module for providing
 * [CrmScreenComponent] in general map
 *
 * @author Alexander Lyutikov
 */
@Module
class CrmScreenApiProvider {

    @Provides
    @IntoMap
    @ApiKey(ICrmApi::class)
    fun providesSelectingModulesScreenApiProvider() = ApiProvider { CrmScreenComponent.create() }
}
