package odoo.miem.android.core.networkApi.employees.api.source

import odoo.miem.android.core.jsonRpcApiFabric.api.JsonRpcApi
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpc
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcArgument
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcPath

/**
 * [IEmployeesService] - interface for making Retrofit instance of employees info
 *
 * @author Egor Danilov
 */
interface IEmployeesService : JsonRpcApi {

    @JsonRpc("call")
    fun getAllEmployees(
        @JsonRpcPath path: String = "web/dataset/search_read",
        @JsonRpcArgument("model") model: String = "hr.employee.public",
        @JsonRpcArgument("fields") fields: List<String> = allEmployeesFields,
        @JsonRpcArgument("limit") limit: Int = 30,
        @JsonRpcArgument("offset") offset: Int = 0,
        @JsonRpcArgument("domain") domain: List<Any> = emptyList()
    ): AllEmployeesResponse

    @JsonRpc("call")
    fun getEmployeeDetails(
        @JsonRpcPath path: String = "web/dataset/call_kw/hr.employee/read",
        @JsonRpcArgument("method") method: String = "read",
        @JsonRpcArgument("model") model: String = "hr.employee.public",
        @JsonRpcArgument("kwargs") kwargs: Map<Any, Any> = emptyMap(),
        @JsonRpcArgument("args") args: List<Any>
    ): List<EmployeeDetailsResponse>

    private companion object {

        val allEmployeesFields = listOf(
            "name",
            "job_title",
            "work_email",
            "work_phone",
        )
    }
}
