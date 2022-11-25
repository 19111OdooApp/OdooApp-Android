package odoo.miem.android.core.retrofitApiFabric

import nl.nl2312.xmlrpc.XmlRpcConverterFactory
import odoo.miem.android.core.retrofitApiFabric.api.RetrofitApi
import retrofit2.Retrofit

// TODO Description
object RetrofitApiResolver {

    private val apiMap by lazy { mutableMapOf<Class<out RetrofitApi>, @JvmSuppressWildcards RetrofitApiProvider>() }

    @Suppress("UNCHECKED_CAST")
    fun <T : RetrofitApi> getRetrofitApi(api: Class<T>): T {
        val apiProvider = apiMap[api] ?: createRetrofitApi(api)
        return apiProvider.get() as T
    }

    private fun <T : RetrofitApi> createRetrofitApi(api: Class<T>): RetrofitApiProvider {
        val newApi = createRetrofitProvider(api)
        apiMap[api] = newApi
        return newApi
    }

    private fun <T : RetrofitApi> createRetrofitProvider(api: Class<T>): RetrofitApiProvider {
        return RetrofitApiProvider { createRetrofitAdapter().create(api) }
    }

    private fun createRetrofitAdapter(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://crm.miem.tv") // TODO get from datastore
            .addConverterFactory(XmlRpcConverterFactory.create())
            .build()
    }
}