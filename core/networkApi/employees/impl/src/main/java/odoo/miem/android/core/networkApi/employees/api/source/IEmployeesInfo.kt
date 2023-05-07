package odoo.miem.android.core.networkApi.employees.api.source

import odoo.miem.android.core.jsonRpcApiFabric.api.JsonRpcApi
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpc
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcArgument
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcPath

/**
 * [IEmployeesInfo] - interface for making Retrofit instance of employees info
 *
 * @author Egor Danilov
 */
interface IEmployeesInfo : JsonRpcApi {

    @JsonRpc("call")
    fun getAllEmployees(
        @JsonRpcPath path: String = "web/dataset/search_read",
        @JsonRpcArgument("model") model: String = "hr.employee",
        @JsonRpcArgument("fields") fields: List<String> = allEmployeesFields
    ): AllEmployeesResponse

    @JsonRpc("call")
    fun getEmployeeInfo(
        @JsonRpcPath path: String = "web/dataset/call_kw/write",
        @JsonRpcArgument("method") method: String = "read",
        @JsonRpcArgument("model") model: String = "hr.employee",
        @JsonRpcArgument("kwargs") kwargs: Map<Any, Any> = emptyMap(),
        @JsonRpcArgument("args") args: List<Any>
    ): List<EmployeeInfoResponse>

    private companion object {

        val allEmployeesFields = listOf(
            "name",
            "job_title",
            "work_email",
            "work_phone",
            "avatar_1920"
        )
    }
}
