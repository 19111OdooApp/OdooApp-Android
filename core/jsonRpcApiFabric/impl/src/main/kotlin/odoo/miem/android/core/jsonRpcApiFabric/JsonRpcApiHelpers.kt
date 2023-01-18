package odoo.miem.android.core.retrofitApiFabric

import odoo.miem.android.core.retrofitApiFabric.api.JsonRpcApi

/**
 * Delegate for lazy properties
 *
 * Usage:
 * val auth by retrofitApi(AuthApi::auth)
 *
 * @author Vorozhtsov Mikhail
 */
inline fun <reified T : JsonRpcApi> jsonRpcApi(): Lazy<T> = lazy { getJsonRpcApi() }

/**
 * Inline function for getting Api
 *
 * Usage:
 * val someRetrofitApi: SomeRetrofitApi = getJsonRpcApi()
 *
 * @author Vorozhtsov Mikhail
 */
inline fun <reified T : JsonRpcApi> getJsonRpcApi(): T = getJsonRpcApi(T::class.java)

/**
 * Inline function for getting Api (java)
 *
 * Usage:
 * val someRetrofitApi: SomeRetrofitApi = getJsonRpcApi()
 *
 * @author Vorozhtsov Mikhail
 */
fun <T : JsonRpcApi> getJsonRpcApi(cls: Class<T>): T = JsonRpcApiResolver.getJsonRpcApi(cls)
