package odoo.miem.android.core.retrofitApiFabric

import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.jsonrpc.core.JsonRpcClient
import odoo.miem.android.core.retrofitApiFabric.api.RetrofitApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

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

    private fun createRetrofitAdapter(): JsonRpcClient {
        // TODO Check url

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor(Timber::d).setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()

        return JsonRpcClient.Builder()
            .baseUrl(dataStore.url)
            .setOkhttpClient(okHttpClient)
            .build()
    }
}
