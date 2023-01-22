package odoo.miem.android.core.jsonrpc.engine.helpers

/**
 * [ProceedMethod] is a container for parameters and headers of
 * request, after proceeding of methods
 *
 * @author Vorozhtsov Mikhail
 */
data class ProceedMethod(
    val params: Map<String, Any?>,
    val headers: List<String>
)
