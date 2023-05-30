
import io.mockk.clearAllMocks
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.runBlocking
import odoo.miem.android.core.networkApi.employees.api.EmployeesRepository
import odoo.miem.android.core.networkApi.employees.api.source.AllEmployeesResponse
import odoo.miem.android.core.networkApi.employees.api.source.EmployeeDetailsResponse
import org.junit.Test
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertIs

class EmployeesRepositoryTest {

    private lateinit var employeesRepository: EmployeesRepository

    @BeforeTest
    fun setup() {
        employeesRepository = EmployeesRepository()
    }

    @Test
    fun `test get all employees pagination`(): Unit = runBlocking {
        val paginationOffset = 0
        val limit = 10

        val result = employeesRepository.getAllEmployees(
            paginationOffset = paginationOffset,
            limit = limit
        )

        assertIs<Single<AllEmployeesResponse>>(result)
    }

    @Test
    fun `test perform employees search`(): Unit = runBlocking {
        val searchRequest = "searchRequest"

        val result = employeesRepository.performEmployeesSearch(
            searchRequest = searchRequest
        )

        assertIs<Single<AllEmployeesResponse>>(result)
    }

    @Test
    fun `test get employee details`(): Unit = runBlocking {
        val employeeId = 1L

        val result = employeesRepository.getEmployeeDetailInfo(
            employeeId = employeeId
        )

        assertIs<Single<List<EmployeeDetailsResponse>>>(result)
    }

    @AfterTest
    fun clear() {
        clearAllMocks()
    }
}