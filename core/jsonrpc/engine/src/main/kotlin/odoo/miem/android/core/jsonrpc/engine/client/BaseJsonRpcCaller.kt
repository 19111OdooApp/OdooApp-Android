package odoo.miem.android.core.jsonrpc.engine.client

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

class BaseJsonRpcCaller(
    private val baseUrl: String,
    private val okHttpClient: OkHttpClient,
    private val requestConverter: RequestConverter,
    private val responseParser: ResponseParser
) : JsonRpcCaller {

    override fun call(jsonRpcRequest: JsonRpcRequest): JsonRpcResponse {
        val requestBody = requestConverter.convert(jsonRpcRequest).toByteArray().toRequestBody()
        val request = Request.Builder()
            .post(requestBody)
            .addHeader("Content-Type", "application/json") // TODO From params
            .url(baseUrl) // TODO Params for Hse...
            .build()

        val response = try {
            okHttpClient.newCall(request).execute()
        } catch (e: Exception) {
            throw NetworkRequestException(
                message = "Network error: ${e.message}",
                cause = e
            )
        }
        return if (response.isSuccessful) {
            response.body?.let { responseParser.parse(it.bytes()) }
                ?: throw IllegalStateException("Response body is null")
        } else {
            throw TransportException(
                httpCode = response.code,
                message = "HTTP ${response.code}. ${response.message}",
                response = response,
            )
        }
    }
}