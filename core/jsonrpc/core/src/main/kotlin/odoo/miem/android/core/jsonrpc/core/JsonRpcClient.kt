package odoo.miem.android.core.jsonrpc.core

import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.jsonrpc.base.engine.JsonRpcCaller
import odoo.miem.android.core.jsonrpc.base.engine.JsonRpcInterceptor
import odoo.miem.android.core.jsonrpc.base.parser.RequestConverter
import odoo.miem.android.core.jsonrpc.base.parser.ResponseParser
import odoo.miem.android.core.jsonrpc.base.parser.ResultParser
import odoo.miem.android.core.jsonrpc.engine.caller.BaseJsonRpcCaller
import odoo.miem.android.core.jsonrpc.engine.helpers.createInvocationHandler
import odoo.miem.android.core.jsonrpc.parser.api.di.IParserApi
import okhttp3.OkHttpClient
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
            requestConverter = builder.requestConverter,
            responseParser = builder.responseParser
        )

        val invocationHandler = createInvocationHandler(
            service = service,
            caller = caller,
            resultParser = builder.resultParser,
            interceptors = builder.interceptors,
            headers = resolveRequestHeaders(),
            logger = Timber::d
        )

        @Suppress("UNCHECKED_CAST")
        return Proxy.newProxyInstance(classLoader, interfaces, invocationHandler) as T
    }

    private fun resolveRequestHeaders(): Map<String, String> = if (dataStore.isHseAuthorized) {
        DEFAULT_REQUEST_HEADERS
    } else {
        DEFAULT_REQUEST_HEADERS // TODO Resolve session_id?
    }

    class Builder {
        internal var caller: JsonRpcCaller? = null
        internal var interceptors: List<JsonRpcInterceptor> = emptyList()
        internal var okHttpClient: OkHttpClient = DEFAULT_OKHTTP_CLIENT
        internal var requestConverter: RequestConverter = DEFAULT_REQUEST_CONVERTER
        internal var responseParser: ResponseParser = DEFAULT_RESPONSE_PARSER
        internal var resultParser: ResultParser = DEFAULT_RESULT_PARSER
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

        fun setRequestConverter(requestConverter: RequestConverter): Builder = apply {
            this.requestConverter = requestConverter
        }

        fun setResponseParser(responseParser: ResponseParser): Builder = apply {
            this.responseParser = responseParser
        }

        fun setResultParser(resultParser: ResultParser): Builder = apply {
            this.resultParser = resultParser
        }

        fun baseUrl(url: String): Builder = apply {
            this.url = url
        }

        fun build(): JsonRpcClient = JsonRpcClient(this)
    }

    private companion object {
        val DEFAULT_OKHTTP_CLIENT by lazy { OkHttpClient.Builder().build() }
        val DEFAULT_REQUEST_CONVERTER by api(IParserApi::requestConverter)
        val DEFAULT_REQUEST_HEADERS by lazy {
            mapOf(
                "Content-Type" to "application/json"
            )
        }
        val DEFAULT_RESPONSE_PARSER by api(IParserApi::responseParser)
        val DEFAULT_RESULT_PARSER by api(IParserApi::resultParser)
        const val DEFAULT_URL = ""
    }
}
