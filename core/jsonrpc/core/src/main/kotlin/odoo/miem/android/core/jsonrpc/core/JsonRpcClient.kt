package odoo.miem.android.core.jsonrpc.core

import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.jsonrpc.base.engine.JsonRpcCaller
import odoo.miem.android.core.jsonrpc.base.engine.JsonRpcInterceptor
import odoo.miem.android.core.jsonrpc.base.engine.protocol.JsonRpcResponse
import odoo.miem.android.core.jsonrpc.converter.api.IDeserializer
import odoo.miem.android.core.jsonrpc.converter.api.ISerializer
import odoo.miem.android.core.jsonrpc.converter.api.di.IConverterApi
import odoo.miem.android.core.jsonrpc.engine.caller.BaseJsonRpcCaller
import odoo.miem.android.core.jsonrpc.engine.helpers.createInvocationHandler
import okhttp3.OkHttpClient
import okhttp3.Response
import timber.log.Timber
import java.lang.reflect.Proxy

/**
 * Extension for creating new implementation of JsonRpc interfaces
 * (like in retrofit)
 *
 * Usage:
 * ```
 * val someCoolApi by create<SomeCoolApi>()
 * ```
 *
 * @author Vorozhtsov Mikhail
 */
inline fun <reified T> JsonRpcClient.create(): T = create(T::class.java)

/**
 * [JsonRpcClient] is a special class, which is create a JsonRpc interfaces,
 * with a help of method [create] and additional info from [Builder]
 *
 * Usage:
 * ```
 * val client = JsonRpcClient.Builder()
 *      .baseUrl(url)
 *      .setOkhttpClient(okHttpClient)
 *      .build()
 *
 * val apiInstance = client.create(apiInterface)
 * ```
 *
 * @author Vorozhtsov Mikhail
 */
class JsonRpcClient internal constructor(
    private val builder: Builder
) {
    private val dataStore by api(IDataStoreApi::dataStore)

    fun <T> create(
        service: Class<T>
    ): T {
        val classLoader = service.classLoader
        val interfaces = arrayOf<Class<*>>(service)
        val caller = builder.caller ?: BaseJsonRpcCaller(
            baseUrl = builder.url,
            okHttpClient = builder.okHttpClient,
            serializer = builder.serializer,
            deserializer = builder.deserializer
        )

        val invocationHandler = createInvocationHandler(
            service = service,
            caller = caller,
            deserializer = builder.deserializer,
            interceptors = builder.interceptors,
            headersResolver = ::resolveRequestHeaders,
            logger = Timber::d,
            onResponseProceedResolver = ::resolveOnResponseProceed
        )

        @Suppress("UNCHECKED_CAST")
        return Proxy.newProxyInstance(classLoader, interfaces, invocationHandler) as T
    }

    private fun resolveRequestHeaders(isAuthRequest: Boolean): Map<String, String> =
        if (isAuthRequest) {
            DEFAULT_REQUEST_HEADERS
        } else {
            DEFAULT_REQUEST_HEADERS + mapOf(FIELD_SESSION_ID to dataStore.sessionId)
        }

    private fun resolveOnResponseProceed(isAuthRequest: Boolean): ((id: Long, Response) -> JsonRpcResponse)? =
        if (isAuthRequest) {
            { id, response ->
                JsonRpcResponse(id, response.headers.values("set-cookie")[0], null)
            }
        } else {
            null
        }

    class Builder {
        internal var caller: JsonRpcCaller? = null
        internal var interceptors: List<JsonRpcInterceptor> = emptyList()
        internal var okHttpClient: OkHttpClient = DEFAULT_OKHTTP_CLIENT
        internal var serializer: ISerializer = DEFAULT_SERIALIZER
        internal var deserializer: IDeserializer = DEFAULT_DESERIALIZER
        internal var url: String = DEFAULT_URL

        fun setCaller(caller: JsonRpcCaller): Builder = apply {
            this.caller = caller
        }

        fun addInterceptor(interceptor: JsonRpcInterceptor): Builder = apply {
            this.interceptors += interceptor
        }

        fun setOkhttpClient(okHttpClient: OkHttpClient): Builder = apply {
            this.okHttpClient = okHttpClient
        }

        fun setSerializer(serializer: ISerializer): Builder = apply {
            this.serializer = serializer
        }

        fun setDeserializer(deserializer: IDeserializer): Builder = apply {
            this.deserializer = deserializer
        }

        fun baseUrl(url: String): Builder = apply {
            this.url = url
        }

        fun build(): JsonRpcClient = JsonRpcClient(this)
    }

    private companion object {
        val DEFAULT_OKHTTP_CLIENT by lazy { OkHttpClient.Builder().build() }
        val DEFAULT_SERIALIZER by api(IConverterApi::serializer)
        val DEFAULT_REQUEST_HEADERS by lazy {
            mapOf(
                "Content-Type" to "application/json"
            )
        }
        val DEFAULT_DESERIALIZER by api(IConverterApi::deserializer)
        const val DEFAULT_URL = ""
        const val FIELD_SESSION_ID = "X-Openerp-Session-Id"
    }
}
