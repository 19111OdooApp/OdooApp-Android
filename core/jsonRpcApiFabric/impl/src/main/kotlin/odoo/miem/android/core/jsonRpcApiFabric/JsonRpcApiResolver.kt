package odoo.miem.android.core.retrofitApiFabric

import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.jsonrpc.core.JsonRpcClient
import odoo.miem.android.core.retrofitApiFabric.api.JsonRpcApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

/**
 * [JsonRpcApiResolver] is a resolver of [JsonRpcApi]
 *
 * It accumulates all instance of [JsonRpcApi] and create new one, if
 * it is not exists
 *
 * @author Vorozhtsov Mikhail
 */
object JsonRpcApiResolver {

    private val apiMap by lazy { mutableMapOf<Class<out JsonRpcApi>, @JvmSuppressWildcards JsonRpcApiProvider>() }
    private val dataStore by api(IDataStoreApi::dataStore)

    @Suppress("UNCHECKED_CAST")
    fun <T : JsonRpcApi> getJsonRpcApi(api: Class<T>): T {
        val apiProvider = apiMap[api] ?: createJsonRpcApi(api)
        return apiProvider.get() as T
    }

    private fun <T : JsonRpcApi> createJsonRpcApi(api: Class<T>): JsonRpcApiProvider {
        val newApi = createJsonRpcProvider(api)
        apiMap[api] = newApi
        return newApi
    }

    private fun <T : JsonRpcApi> createJsonRpcProvider(api: Class<T>): JsonRpcApiProvider {
        return JsonRpcApiProvider { createJsonRpcAdapter().create(api) }
    }

    private fun createJsonRpcAdapter(): JsonRpcClient {

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
