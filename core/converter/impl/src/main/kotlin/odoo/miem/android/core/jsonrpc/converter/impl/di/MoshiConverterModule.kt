package odoo.miem.android.core.jsonrpc.converter.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.core.jsonrpc.converter.api.IDeserializer
import odoo.miem.android.core.jsonrpc.converter.api.ISerializer
import odoo.miem.android.core.jsonrpc.converter.impl.moshi.MoshiDeserializer
import odoo.miem.android.core.jsonrpc.converter.impl.moshi.MoshiSerializer

/**
 * [MoshiParserModule] - module for providing instance of [MoshiSerializer],
 * [MoshiResponseParser] and [MoshiDeserializer] in **DI graph**
 *
 * @author Vorozhtsov Mikhail
 */
@Module
interface MoshiConverterModule {

    @Binds
    fun provideMoshiSerializer(serializer: MoshiSerializer): ISerializer

    @Binds
    fun provideMoshiDeserializer(deserializer: MoshiDeserializer): IDeserializer
}
