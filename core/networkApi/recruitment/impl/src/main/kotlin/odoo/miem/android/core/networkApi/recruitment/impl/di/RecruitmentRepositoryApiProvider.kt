package odoo.miem.android.core.networkApi.recruitment.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider
import odoo.miem.android.core.networkApi.recruitment.api.di.IRecruitmentRepositoryApi

/**
 * [RecruitmentRepositoryApiProvider] - **Dagger** module for providing
 * [RecruitmentRepositoryComponent] in general map
 *
 * @author Vorozhtso Mikhail
 */
@Module
class RecruitmentRepositoryApiProvider {

    @Provides
    @IntoMap
    @ApiKey(IRecruitmentRepositoryApi::class)
    fun provideRecruitmentRepositoryApiProvider() = ApiProvider { RecruitmentRepositoryComponent.create() }
}
