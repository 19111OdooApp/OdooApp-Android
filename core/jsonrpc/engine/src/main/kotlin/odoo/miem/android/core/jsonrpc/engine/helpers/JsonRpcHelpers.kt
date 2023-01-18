package odoo.miem.android.core.jsonrpc.engine.helpers

import odoo.miem.android.core.jsonrpc.base.engine.JsonRpcCaller
import odoo.miem.android.core.jsonrpc.base.engine.JsonRpcInterceptor
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonArgument
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpc
import odoo.miem.android.core.jsonrpc.base.engine.exception.JsonRpcException
import odoo.miem.android.core.jsonrpc.base.engine.protocol.JsonRpcRequest
import odoo.miem.android.core.jsonrpc.engine.interceptor.RealInterceptorChain
import odoo.miem.android.core.jsonrpc.engine.interceptor.ServerCallInterceptor
import odoo.miem.android.core.jsonrpc.base.parser.ResultParser
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicLong


val requestId = AtomicLong(0)

// TODO Description
// TODO Add new params
internal fun Method.jsonRpcParameters(args: Array<Any?>?, service: Class<*>): Map<String, Any?> {
    return parameterAnnotations
        .map { annotation ->
            annotation?.firstOrNull {
                JsonArgument::class.java.isInstance(it)
            }
        }
        .mapIndexed { index, annotation ->
            when (annotation) {
                is JsonArgument -> annotation.value
                else -> throw IllegalStateException(
                    "Argument #$index of ${service.name}#$name()" +
                            " must be annotated with @${JsonArgument::class.java.simpleName}"
                )
            }
        }
        .mapIndexed { i, name -> name to args?.get(i) }
        .associate { it }
}

// TODO Description
fun <T> createInvocationHandler(
    service: Class<T>,
    caller: JsonRpcCaller,
    resultParser: ResultParser,
    interceptors: List<JsonRpcInterceptor> = listOf(),
    logger: (String) -> Unit = { _ -> }
): InvocationHandler {
    return object : InvocationHandler {

        override fun invoke(proxy: Any, method: Method, args: Array<Any?>?): Any {
            val methodAnnotation =
                method.getAnnotation(JsonRpc::class.java)
                    ?: throw IllegalStateException("Method should be annotated with JsonRpc annotation")

            val id = requestId.incrementAndGet()
            val methodName = methodAnnotation.value
            val parameters = method.jsonRpcParameters(args, service)

            val request = JsonRpcRequest(id, methodName, parameters)

            //add interceptor, which makes network call
            val serverCallInterceptor = ServerCallInterceptor(caller)
            val finalInterceptors = interceptors.plus(serverCallInterceptor)

            val chain = RealInterceptorChain(caller, finalInterceptors, request)

            val response = chain.interceptors.first().intercept(chain)

            val returnType: Type = if (method.genericReturnType is ParameterizedType) {
                method.genericReturnType
            } else {
                method.returnType
            }
            logger("JsonRPC: Parsing $returnType")

            val result = response.result

            if (result != null) {
                return resultParser.parse(returnType, result)
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
