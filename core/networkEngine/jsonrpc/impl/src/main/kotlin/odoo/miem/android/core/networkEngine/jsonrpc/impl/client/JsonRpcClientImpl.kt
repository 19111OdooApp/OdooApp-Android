package odoo.miem.android.core.networkEngine.jsonrpc.impl.client

import odoo.miem.android.core.networkEngine.jsonrpc.api.JsonRpcClient
import odoo.miem.android.core.networkEngine.jsonrpc.api.exception.NetworkRequestException
import odoo.miem.android.core.networkEngine.jsonrpc.api.exception.TransportException
import odoo.miem.android.core.networkEngine.jsonrpc.api.protocol.JsonRpcRequest
import odoo.miem.android.core.networkEngine.jsonrpc.api.protocol.JsonRpcResponse
import odoo.miem.android.core.networkEngine.parser.api.RequestConverter
import odoo.miem.android.core.networkEngine.parser.api.ResponseParser
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class JsonRpcClientImpl(
    private val baseUrl: String,
    private val okHttpClient: OkHttpClient,
    private val requestConverter: RequestConverter,
    private val responseParser: ResponseParser
) : JsonRpcClient {

    override fun call(jsonRpcRequest: JsonRpcRequest): JsonRpcResponse {
        val requestBody = requestConverter.convert(jsonRpcRequest).toByteArray().toRequestBody()
        val request = Request.Builder()
            .post(requestBody)
            .addHeader("Content-Type", "application/json") // TODO From params
            .url(baseUrl)
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