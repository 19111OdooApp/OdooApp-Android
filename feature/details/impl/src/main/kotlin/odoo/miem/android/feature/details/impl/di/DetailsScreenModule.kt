package odoo.miem.android.feature.details.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.feature.details.api.IDetailsScreen
import odoo.miem.android.feature.details.impl.DetailsScreen

/**
 * [DetailsScreenModule] - module for providing instance of [DetailsScreen]
 * in **DI graph**
 *
 * @author Vorozhtsov Mikhail
 */
@Module
interface DetailsScreenModule {

    @Binds
    fun provideDetailsScreen(detailsScreen: DetailsScreen): IDetailsScreen
}
