package odoo.miem.android.core.networkEngine.parser.impl.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides

/**
 * [MoshiModule] - **Dagger** module for providing [Moshi] in general map
 *
 * @author Vorozhtso Mikhail
 */
@Module
class MoshiModule {

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
}