package odoo.miem.android.common.network.crm.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.common.network.crm.api.di.ICrmInteractorApi
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider

/**
 * [CrmInteractorApiProvider] - **Dagger** module for providing
 * [CrmInteractorComponent] in general map
 *
 * @author Vorozhtsov Mikhail
 */
@Module
class CrmInteractorApiProvider {

    @Provides
    @IntoMap
    @ApiKey(ICrmInteractorApi::class)
    fun provideCrmUseCaseApiProvider() = ApiProvider { CrmInteractorComponent.create() }
}
