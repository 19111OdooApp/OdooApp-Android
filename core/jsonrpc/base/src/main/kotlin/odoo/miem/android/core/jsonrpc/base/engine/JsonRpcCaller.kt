package odoo.miem.android.core.jsonrpc.base.engine

import odoo.miem.android.core.jsonrpc.base.engine.protocol.JsonRpcRequest
import odoo.miem.android.core.jsonrpc.base.engine.protocol.JsonRpcResponse
import okhttp3.Response

/**
 * [JsonRpcCaller] is a client, with a help of which
 * we create and send request
 *
 * @author Vorozhtsov Mikhail
 */
interface JsonRpcCaller {

    /**
     * This method will create and send request, with a help
     * of additional information:
     *
     * @param jsonRpcRequest is instance of [JsonRpcRequest]
     * @param headers is map of headers, which we need to insert in request.
     * For example, `session_id`
     * @param paths is additional path for url. For example:
     * ```
     * val baseUrl = "https://odoo.com/"
     * val paths = listOf("web", "dataset")
     * ```
     * Result: `https://odoo.com/web/dataset/`
     *
     */
    fun call(
        jsonRpcRequest: JsonRpcRequest,
        headers: Map<String, String> = emptyMap(),
        paths: List<String> = emptyList(),
        onResponseProceed: ((id: Long, Response) -> JsonRpcResponse)? = null
    ): JsonRpcResponse
}
