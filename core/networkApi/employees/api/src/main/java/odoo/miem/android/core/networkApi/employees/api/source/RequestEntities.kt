package odoo.miem.android.core.networkApi.employees.api.source

/**
 * json model of request is [ [idOfEmployee], [fields] ]
 *
 * @param idOfEmployee - Int
 *
 * @param fields - list of strings with fields you want to get
 */
data class GetEmployeeInfoRequest(
    val args: List<Any>
)