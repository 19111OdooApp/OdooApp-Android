package odoo.miem.android.core.jsonrpc.base.engine.protocol

/**
 * [JsonRpcRequest] is a protocol of response, which is
 * an request format
 *
 * For example (json view):
 * ```
 * { "id" : 1, "method" : "call", "params": { ... }, "jsonrpc" : "2.0"}
 * ```
 *
 * So, [JsonRpcRequest] will contains [id], [method], [params] and [jsonrpc] from example above
 *
 * @author Vorozhtsov Mokhail
 */
data class JsonRpcRequest(
    val id: Long,
    val method: String,
    val params: Map<String, Any?> = emptyMap(),
    val jsonrpc: String = "2.0"
)