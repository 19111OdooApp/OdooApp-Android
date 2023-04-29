package odoo.miem.android.core.jsonrpc.engine.helpers

import odoo.miem.android.core.jsonrpc.base.engine.JsonRpcCaller
import odoo.miem.android.core.jsonrpc.base.engine.JsonRpcInterceptor
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpc
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcArgument
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcAuthentication
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcPath
import odoo.miem.android.core.jsonrpc.base.engine.exception.JsonRpcException
import odoo.miem.android.core.jsonrpc.base.engine.protocol.JsonRpcRequest
import odoo.miem.android.core.jsonrpc.base.engine.protocol.JsonRpcResponse
import odoo.miem.android.core.jsonrpc.base.parser.ResultParser
import odoo.miem.android.core.jsonrpc.engine.interceptor.RealInterceptorChain
import odoo.miem.android.core.jsonrpc.engine.interceptor.ServerCallInterceptor
import okhttp3.Response
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicLong

val requestId = AtomicLong(0)

/**
 * Extension for resolving all data from annotation in method and
 * convert it to the map of json body request or path
 *
 * @author Vorozhtsov Mikhail
 */
internal fun Method.jsonRpcParameters(args: Array<Any?>?, service: Class<*>): ProceedMethod {
    val params = mutableMapOf<String, Any?>()
    val headers = mutableListOf<String>()

    parameterAnnotations
        .flatten()
        .mapIndexed { index, annotation ->
            when (annotation) {
                is JsonRpcArgument -> params[annotation.value] = args?.get(index)
                is JsonRpcPath -> headers.add(args?.get(index) as String)
                else ->
                    throw IllegalStateException(
                        "Argument #$index of ${service.name}#$name()" +
                            " must be annotated with @${JsonRpcArgument::class.java.simpleName}"
                    )
            }
        }

    return ProceedMethod(params, headers)
}

/**
 * Function for creating [InvocationHandler], which will
 * create, send and convert final result
 *
 * @author Vorozhtsov Mikhail
 */
fun <T> createInvocationHandler(
    service: Class<T>,
    caller: JsonRpcCaller,
    resultParser: ResultParser,
    interceptors: List<JsonRpcInterceptor> = listOf(),
    headersResolver: (isAuthRequest: Boolean) -> Map<String, String> = { emptyMap() },
    logger: (String) -> Unit = { _ -> },
    onResponseProceedResolver: (isAuthRequest: Boolean) -> ((id: Long, Response) -> JsonRpcResponse)? = { null },
): InvocationHandler {
    return object : InvocationHandler {

        override fun invoke(proxy: Any, method: Method, args: Array<Any?>?): Any {
            val methodAnnotation =
                method.getAnnotation(JsonRpc::class.java)
                    ?: error("Method should be annotated with JsonRpc annotation")

            val methodAuthentication = method.getAnnotation(JsonRpcAuthentication::class.java)
            val headers = headersResolver(methodAuthentication != null)
            val onResponseProceed = onResponseProceedResolver(methodAuthentication != null)

            val id = requestId.incrementAndGet()
            val methodName = methodAnnotation.value
            val proceedMethod = method.jsonRpcParameters(args, service)

            val request = JsonRpcRequest(id, methodName, proceedMethod.params)

            // add interceptor, which makes network call
            val serverCallInterceptor = ServerCallInterceptor(
                client = caller,
                headers = headers,
                paths = proceedMethod.headers,
                onResponseProceed = onResponseProceed
            )
            val finalInterceptors = interceptors.plus(serverCallInterceptor)

            val chain = RealInterceptorChain(finalInterceptors, request)

            val response = chain.interceptors.first().intercept(chain)

            val returnType: Type = if (method.genericReturnType is ParameterizedType) {
                method.genericReturnType
            } else {
                method.returnType
            }
            logger("JsonRPC: Parsing $returnType")

            val result = response.result

            if (result != null) {
                return resultParser.deserialize(returnType, result)
            } else {
                val error = response.error
                checkNotNull(error)

                throw JsonRpcException(
                    error.message,
                    error.code,
                    error.data
                )
            }
        }
    }
}
