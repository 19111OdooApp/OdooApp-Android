package odoo.miem.android.core.jsonrpc.parser.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.core.jsonrpc.base.parser.RequestConverter
import odoo.miem.android.core.jsonrpc.base.parser.ResponseParser
import odoo.miem.android.core.jsonrpc.base.parser.ResultParser
import odoo.miem.android.core.jsonrpc.parser.impl.moshi.MoshiRequestConverter
import odoo.miem.android.core.jsonrpc.parser.impl.moshi.MoshiResponseParser
import odoo.miem.android.core.jsonrpc.parser.impl.moshi.MoshiResultParser

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