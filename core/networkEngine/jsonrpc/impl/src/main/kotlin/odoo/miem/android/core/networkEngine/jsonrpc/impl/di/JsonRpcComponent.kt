package odoo.miem.android.core.networkEngine.jsonrpc.impl.di

import dagger.Component
import odoo.miem.android.core.networkEngine.jsonrpc.api.di.IJsonRpcApi

/**
 * [JsonRpcComponent] - **Dagger** component, which implements interface [IJsonRpcApi]
 * Providing in general **DI graph** with a help of [JsonRpcApiProvider].
 *
 * Included modules:
 *  - [JsonRpcModule] - provide [AuthorizationRepository] in *DI graph*
 *
 * @author Vorozhtsov Mikhail
 */
@Component(
    modules = [
        JsonRpcModule::class
    ]
)
interface JsonRpcComponent : IJsonRpcApi {
    companion object {
        fun create(): IJsonRpcApi = DaggerJsonRpcComponent.builder().build()
    }
}
