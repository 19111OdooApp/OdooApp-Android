package odoo.miem.android.core.networkApi.crm.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider
import odoo.miem.android.core.networkApi.crm.api.di.ICrmRepositoryApi

/**
 * [CrmRepositoryApiProvider] - **Dagger** module for providing
 * [CrmRepositoryComponent] in general map
 *
 * @author Vorozhtso Mikhail
 */
@Module
class CrmRepositoryApiProvider {

    @Provides
    @IntoMap
    @ApiKey(ICrmRepositoryApi::class)
    fun provideCrmtRepositoryApiProvider() = ApiProvider { CrmRepositoryComponent.create() }
}
