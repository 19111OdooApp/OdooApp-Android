package odoo.miem.android.core.jsonrpc.converter.impl.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import odoo.miem.android.core.jsonrpc.converter.api.annotation.SpecifiedTypeOrNull
import javax.inject.Singleton

/**
 * [MoshiModule] - **Dagger** module for providing [Moshi] in general map
 *
 * @author Vorozhtso Mikhail
 */
@Module
class MoshiModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(SpecifiedTypeOrNull.Adapter.Factory())
        .add(KotlinJsonAdapterFactory())
        .build()
}
