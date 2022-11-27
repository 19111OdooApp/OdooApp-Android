package odoo.miem.android.core.retrofitApiFabric

import odoo.miem.android.core.retrofitApiFabric.api.RetrofitApi

/**
 * Delegate for lazy properties
 *
 * Usage:
 * val auth by retrofitApi(AuthApi::auth)
 *
 * @author Vorozhtsov Mikhail
 */
inline fun <reified T : RetrofitApi> retrofitApi(): Lazy<T> = lazy { getRetrofitApi() }

/**
 * Inline function for getting Api
 *
 * Usage:
 * val someRetrofitApi: SomeRetrofitApi = getRetrofitApi()
 *
 * @author Vorozhtsov Mikhail
 */
inline fun <reified T : RetrofitApi> getRetrofitApi(): T = getRetrofitApi(T::class.java)

/**
 * Inline function for getting Api (java)
 *
 * Usage:
 * val someRetrofitApi: SomeRetrofitApi = getRetrofitApi()
 *
 * @author Vorozhtsov Mikhail
 */
fun <T : RetrofitApi> getRetrofitApi(cls: Class<T>): T = RetrofitApiResolver.getRetrofitApi(cls)