package odoo.miem.android.common.network.recruitment.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.common.network.recruitment.api.di.IRecruitmentInteractorApi
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider

/**
 * [RecruitmentInteractorApiProvider] - **Dagger** module for providing
 * [RecruitmentInteractorComponent] in general map
 *
 * @author Vorozhtsov Mikhail
 */
@Module
class RecruitmentInteractorApiProvider {

    @Provides
    @IntoMap
    @ApiKey(IRecruitmentInteractorApi::class)
    fun provideRecruitmentUseCaseApiProvider() = ApiProvider { RecruitmentInteractorComponent.create() }
}
