package odoo.miem.android.core.retrofitApiFabric

import odoo.miem.android.core.retrofitApiFabric.api.RetrofitApi

// TODO Description
inline fun <reified T : RetrofitApi> retrofitApi(): Lazy<T> = lazy { getRetrofitApi() }

// TODO Description
inline fun <reified T : RetrofitApi> getRetrofitApi(): T = getRetrofitApi(T::class.java)

// TODO Description
fun <T : RetrofitApi> getRetrofitApi(cls: Class<T>): T = RetrofitApiResolver.getRetrofitApi(cls)