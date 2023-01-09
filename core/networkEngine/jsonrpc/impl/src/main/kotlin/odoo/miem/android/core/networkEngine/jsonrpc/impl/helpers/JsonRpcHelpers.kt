package odoo.miem.android.core.networkEngine.jsonrpc.impl.helpers

import odoo.miem.android.core.networkEngine.jsonrpc.api.JsonRpcClient
import odoo.miem.android.core.networkEngine.jsonrpc.api.JsonRpcInterceptor
import odoo.miem.android.core.networkEngine.jsonrpc.api.annotation.JsonRpc
import odoo.miem.android.core.networkEngine.jsonrpc.api.exception.JsonRpcException
import odoo.miem.android.core.networkEngine.jsonrpc.api.protocol.JsonRpcRequest
import odoo.miem.android.core.networkEngine.jsonrpc.impl.interceptor.RealInterceptorChain
import odoo.miem.android.core.networkEngine.jsonrpc.impl.interceptor.ServerCallInterceptor
import odoo.miem.android.core.networkEngine.parser.api.ResultParser
import java.lang.reflect.*
import java.util.concurrent.atomic.AtomicLong


val requestId = AtomicLong(0)


fun <T> createJsonRpcService(
    service: Class<T>,
    client: JsonRpcClient,
    resultParser: ResultParser,
    interceptors: List<JsonRpcInterceptor> = listOf(),
    logger: (String) -> Unit = {}
): T {
    val classLoader = service.classLoader
    val interfaces = arrayOf<Class<*>>(service)
    val invocationHandler = createInvocationHandler(
        service,
        client,
        resultParser,
        interceptors,
        logger
    )

    @Suppress("UNCHECKED_CAST")
    return Proxy.newProxyInstance(classLoader, interfaces, invocationHandler) as T
}

private fun Method.jsonRpcParameters(args: Array<Any?>?, service: Class<*>): Map<String, Any?> {
    return parameterAnnotations
        .map { annotation -> annotation?.firstOrNull { JsonRpc::class.java.isInstance(it) } }
        .mapIndexed { index, annotation ->
            when (annotation) {
                is JsonRpc -> annotation.value
                else -> throw IllegalStateException(
                    "Argument #$index of ${service.name}#$name()" +
                            " must be annotated with @${JsonRpc::class.java.simpleName}"
                )
            }
        }
        .mapIndexed { i, name -> name to args?.get(i) }
        .associate { it }
}

private fun <T> createInvocationHandler(
    service: Class<T>,
    client: JsonRpcClient,
    resultParser: ResultParser,
    interceptors: List<JsonRpcInterceptor> = listOf(),
    logger: (String) -> Unit
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
            val serverCallInterceptor = ServerCallInterceptor(client)
            val finalInterceptors = interceptors.plus(serverCallInterceptor)

            val chain = RealInterceptorChain(client, finalInterceptors, request)

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
