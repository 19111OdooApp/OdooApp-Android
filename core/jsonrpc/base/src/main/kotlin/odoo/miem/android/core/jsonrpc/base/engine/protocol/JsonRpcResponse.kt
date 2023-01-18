package odoo.miem.android.core.jsonrpc.base.engine.protocol

/**
 * [JsonRpcResponse] is a protocol of response, which is
 * an error format
 *
 * For example (json view):
 * ```
 * { "id" : 1, "result" : "", "error" : { "message": "Error", "code" : 404, "data" : "Some addition info" }
 * ```
 *
 * So, [JsonRpcResponse] will contains [id], [result] and [error] from example above
 *
 * @author Vorozhtsov Mokhail
 */
data class JsonRpcResponse(
    val id: Long,
    val result: Any?,
    val error: JsonRpcError?
)