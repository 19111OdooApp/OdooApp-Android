package odoo.miem.android.core.jsonrpc.base.engine.protocol

/**
 * [JsonRpcError] is a protocol of response, which is
 * an error format
 *
 * For example (json view):
 * ```
 * { "id" : 1, "result" : "", "error" : { "message": "Error", "code" : 404, "data" : "Some addition info" }
 * ```
 *
 * So, [JsonRpcError] will contains [message], [code] and [data] from example above
 *
 * @author Vorozhtsov Mokhail
 */
data class JsonRpcError(
    val message: String,
    val code: Int,
    val data: Any?
)