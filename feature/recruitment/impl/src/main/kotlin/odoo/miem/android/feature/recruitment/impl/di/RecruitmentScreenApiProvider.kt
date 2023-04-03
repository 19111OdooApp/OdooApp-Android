package odoo.miem.android.feature.recruitment.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider
import odoo.miem.android.feature.recruitment.api.di.IRecruitmentApi

/**
 * [RecruitmentScreenApiProvider] - **Dagger** module for providing
 * [RecruitmentScreenComponent] in general map
 *
 * @author Alexander Lyutikov
 */
@Module
class RecruitmentScreenApiProvider {

    @Provides
    @IntoMap
    @ApiKey(IRecruitmentApi::class)
    fun providesSelectingModulesScreenApiProvider() = ApiProvider { RecruitmentScreenComponent.create() }
}
