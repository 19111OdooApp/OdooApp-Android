package odoo.miem.android.core.jsonrpc.engine.caller

import odoo.miem.android.core.jsonrpc.base.engine.JsonRpcCaller
import odoo.miem.android.core.jsonrpc.base.engine.exception.NetworkRequestException
import odoo.miem.android.core.jsonrpc.base.engine.exception.TransportException
import odoo.miem.android.core.jsonrpc.base.engine.protocol.JsonRpcRequest
import odoo.miem.android.core.jsonrpc.base.engine.protocol.JsonRpcResponse
import odoo.miem.android.core.jsonrpc.base.parser.RequestConverter
import odoo.miem.android.core.jsonrpc.base.parser.ResponseParser
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * [BaseJsonRpcCaller] is a base implementation of [JsonRpcCaller]
 *
 * @author Vorozhtsov Mikhail
 */
class BaseJsonRpcCaller(
    private val baseUrl: String,
    private val okHttpClient: OkHttpClient,
    private val requestConverter: RequestConverter,
    private val responseParser: ResponseParser
) : JsonRpcCaller {

    override fun call(
        jsonRpcRequest: JsonRpcRequest,
        headers: Map<String, String>,
        paths: List<String>
    ): JsonRpcResponse {
        val requestBody = requestConverter.convert(jsonRpcRequest).toByteArray().toRequestBody()
        val request = Request.Builder()
            .post(requestBody)
            .setHeaders(headers)
            .setUrlWithPath(
                baseUrl = baseUrl,
                paths = paths
            )
            .build()

        val response = runCatching {
            okHttpClient.newCall(request).execute()
        }.getOrElse { e ->
            throw NetworkRequestException(
                message = "Network error: ${e.message}",
                cause = e
            )
        }
        return if (response.isSuccessful) {
            response.body?.let { responseParser.parse(it.bytes()) }
                ?: error("Response body is null")
        } else {
            throw TransportException(
                httpCode = response.code,
                message = "HTTP ${response.code}. ${response.message}",
                response = response,
            )
        }
    }

    private fun Request.Builder.setHeaders(headers: Map<String, String>): Request.Builder = apply {
        for ((key, value) in headers) {
            addHeader(key, value)
        }
    }

    private fun Request.Builder.setUrlWithPath(
        baseUrl: String,
        paths: List<String>
    ): Request.Builder = apply {
        url(
            baseUrl + if (paths.isNotEmpty()) {
                paths.joinToString(
                    separator = DEFAULT_SEPARATOR,
                    postfix = DEFAULT_POSTFIX
                )
            } else {
                DEFAULT_PATH
            }
        )
    }

    private companion object {
        const val DEFAULT_SEPARATOR = "/"
        const val DEFAULT_POSTFIX = "/"
        const val DEFAULT_PATH = ""
    }
}
