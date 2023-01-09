package odoo.miem.android.core.networkEngine.jsonrpc.api.di

import odoo.miem.android.core.di.api.Api
import odoo.miem.android.core.networkEngine.jsonrpc.api.JsonRpcClient
import odoo.miem.android.core.networkEngine.jsonrpc.api.JsonRpcInterceptor

/**
 * [IJsonRpcApi] needed for wrapping over [JsonRpcClient],
 * [JsonRpcInterceptor] and providing in common **DI graph**
 *
 * @see Api
 *
 * @author Vorozhtsov Mikhail
 */
interface IJsonRpcApi : Api {

    // TODO Do we need them?
    // val jsonRpcClient: JsonRpcClient
    // val jsonRpcInterceptor: JsonRpcInterceptor
}
