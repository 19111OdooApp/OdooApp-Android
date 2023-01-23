package odoo.miem.android.core.jsonrpc.base.engine.annotation

/**
 * [JsonRpc] is annotation, which provide method in
 * json body of request
 *
 * @param value is name of method
 *
 * For example:
 * ```
 * @JsonRpc("call")
 * fun getCoolUser(): User
 * ```
 *
 * So, in example above we will find such result:
 * ```
 * { "id": 1, "method": "call", .... }
 * ```
 *
 * @author Vorozhtsov Mikhail
 */
annotation class JsonRpc(val value: String)

/**
 * [JsonRpcArgument] is annotation, which provide parameters
 * in `params` field of json body.
 *
 * @param value contains name of field
 *
 * For example:
 * ```
 * @JsonRpc("call")
 * fun getCoolUser(
 *      @JsonRpcArgument("name") name: String = "Mike"
 * ): User
 * ```
 *
 * So, in example above we will find such result:
 * ```
 * { "id": 1, "method": "call", "params" : { "name" : "Mike" }, ... }
 * ```
 *
 * @author Vorozhtsov Mikhail
 */
annotation class JsonRpcArgument(val value: String)

/**
 * [JsonRpcPath] is annotation, which provide path in request url
 *
 *
 * For example:
 * ```
 * val baseUrl = "https://odoo.com/"
 *
 * @JsonRpc("call")
 * fun getCoolUser(
 *      @JsonRpcPath path: String = "web"
 *      @JsonRpcPath path: String = "dataset"
 * ): User
 * ```
 *
 * So, in example above final url will be:
 * ```
 * https://odoo.com/web/dataset/
 * ```
 *
 * It is also possible make path, and we will have same result url:
 * ```
 * @JsonRpc("call")
 * fun getCoolUser(
 *      @JsonRpcPath path: String = "web/dataset"
 * ): User
 * ```
 *
 * @author Vorozhtsov Mikhail
 */
annotation class JsonRpcPath
