package odoo.miem.android.core.retrofitApiFabric

import nl.nl2312.xmlrpc.XmlRpcConverterFactory
import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.retrofitApiFabric.api.RetrofitApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory

/**
 * [RetrofitApiResolver] is a resolver of [RetrofitApi]
 *
 * It accumulates all instance of [RetrofitApi] and create new one, if
 * it is not exists
 *
 * @author Vorozhtsov Mikhail
 */
object RetrofitApiResolver {

    private val apiMap by lazy { mutableMapOf<Class<out RetrofitApi>, @JvmSuppressWildcards RetrofitApiProvider>() }
    private val dataStore by api(IDataStoreApi::dataStore)

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
        // TODO Check url
        return Retrofit.Builder()
            .baseUrl(dataStore.url)
            .addConverterFactory(XmlRpcConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }
}
