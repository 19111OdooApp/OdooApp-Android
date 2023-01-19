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
 * ATTENTION
 * Depending on the type of authorization (hse or general, for example),
 * this method will be in body of `params` map or like in example above
 *
 * @author Vorozhtsov Mikhail
 */
annotation class JsonRpc(val value: String)

/**
 * [JsonArgument] is annotation, which provide parameters
 * in `params` field of json body.
 *
 * @param value contains name of field
 *
 * For example:
 * ```
 * @JsonRpc("call")
 * fun getCoolUser(
 *      @JsonArgument("name") name: String = "Mike"
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
annotation class JsonArgument(val value: String)
