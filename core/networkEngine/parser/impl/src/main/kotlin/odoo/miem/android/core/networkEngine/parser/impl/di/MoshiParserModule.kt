package odoo.miem.android.core.networkEngine.parser.impl.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import odoo.miem.android.core.networkEngine.parser.api.RequestConverter
import odoo.miem.android.core.networkEngine.parser.api.ResponseParser
import odoo.miem.android.core.networkEngine.parser.api.ResultParser
import odoo.miem.android.core.networkEngine.parser.impl.moshi.MoshiRequestConverter
import odoo.miem.android.core.networkEngine.parser.impl.moshi.MoshiResponseParser
import odoo.miem.android.core.networkEngine.parser.impl.moshi.MoshiResultParser

/**
 * [MoshiParserModule] - module for providing instance of [MoshiRequestConverter],
 * [MoshiResponseParser] and [MoshiResultParser] in **DI graph**
 *
 * @author Vorozhtsov Mikhail
 */
@Module
interface MoshiParserModule {

    @Binds
    fun provideMoshiRequestConverter(moshiRequestConverter: MoshiRequestConverter): RequestConverter

    @Binds
    fun provideMoshiResponseParser(moshiResponseParser: MoshiResponseParser): ResponseParser

    @Binds
    fun provideAuthorizationRepository(moshiResultParser: MoshiResultParser): ResultParser
}