package odoo.miem.android.core.jsonrpc.base.engine

import odoo.miem.android.core.jsonrpc.base.engine.protocol.JsonRpcRequest
import odoo.miem.android.core.jsonrpc.base.engine.protocol.JsonRpcResponse

/**
 * [JsonRpcInterceptor] is a special interface, with
 * a help of witch we can make a [Chain] of a processing
 * request and response. It can work (depend on implementation)
 * recursively, for example:
 * ```
 * chain1 - intercept()
 *             |
 *             -> chain2 - intercept()
 *                             |
 *                             -> chain3 ...
 *                                        |
 *                                        -> call()
 *                                             |
 *                            JsonRpcResponse <-
 * ```
 *
 * @Vorozhtsov Mikhail
 */
interface JsonRpcInterceptor {

    /**
     * This method processes current [chain] and
     * return new (or not) [JsonRpcResponse]
     */
    fun intercept(chain: Chain): JsonRpcResponse

    /**
     * [Chain] is interface of current block of [JsonRpcInterceptor]
     */
    interface Chain {

        /**
         * This method call [JsonRpcInterceptor.intercept] and move
         * to next chain
         */
        fun proceed(request: JsonRpcRequest): JsonRpcResponse

        /**
         * Just return current [JsonRpcRequest] of chain
         */
        fun request(): JsonRpcRequest
    }
}