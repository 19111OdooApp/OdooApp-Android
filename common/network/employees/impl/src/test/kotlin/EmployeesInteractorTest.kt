import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.runBlocking
import odoo.miem.android.common.network.employees.api.entities.AllEmployeesBasicInfo
import odoo.miem.android.common.network.employees.api.entities.EmployeeBasicInfo
import odoo.miem.android.common.network.employees.api.entities.EmployeeDetails
import odoo.miem.android.common.network.employees.impl.EmployeesInteractor
import odoo.miem.android.common.utils.avatar.AvatarRequestHeader
import odoo.miem.android.common.utils.avatar.getAvatarRequestHeaders
import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.di.impl.ApiRegistry
import odoo.miem.android.core.networkApi.employees.api.di.IEmployeesRepositoryApi
import odoo.miem.android.core.networkApi.employees.api.source.AllEmployeesResponse
import odoo.miem.android.core.utils.state.ResultSingle
import odoo.miem.android.core.utils.state.SuccessResult
import org.junit.Test
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertIs

class EmployeesInteractorTest {

    private lateinit var employeesInteractor: EmployeesInteractor

    private lateinit var mockEmployeesRepositoryApi: IEmployeesRepositoryApi
    private lateinit var mockDataStoreApi: IDataStoreApi

    @BeforeTest
    fun setup() {
        mockEmployeesRepositoryApi = mockk(relaxed = true)
        mockDataStoreApi = mockk(relaxed = true)

        mockkObject(ApiRegistry)
        every { ApiRegistry.getApi(IEmployeesRepositoryApi::class.java) } returns mockEmployeesRepositoryApi
        every { ApiRegistry.getApi(IDataStoreApi::class.java) } returns mockDataStoreApi

        employeesInteractor = EmployeesInteractor()
    }

    @Test
    fun `test get employees info`(): Unit = runBlocking {
        val paginationOffset = 0

        val result = employeesInteractor.getAllEmployeesInfo(
            paginationOffset = paginationOffset
        )

        assertIs<ResultSingle<AllEmployeesBasicInfo>>(result)
    }

    @Test
    fun `test get employees info failed`(): Unit = runBlocking {
        val paginationOffset = 0

        coEvery {
            mockEmployeesRepositoryApi.employeesRepository.getAllEmployees(paginationOffset)
        } throws RuntimeException()

        assertFails {
            employeesInteractor.getAllEmployeesInfo(
                paginationOffset = paginationOffset
            )
        }
    }

    @Test
    fun `test get employees info expected result`(): Unit = runBlocking {
        val paginationOffset = 0

        val mockAllEmployeesResponse = mockk<AllEmployeesResponse>(relaxed = true)

        coEvery {
            mockEmployeesRepositoryApi.employeesRepository.getAllEmployees(paginationOffset)
        } returns Single.just(mockAllEmployeesResponse)

        val result = employeesInteractor.getAllEmployeesInfo(
            paginationOffset = paginationOffset
        )

        assertIs<Single<SuccessResult<AllEmployeesBasicInfo>>>(result)
        val actualData = requireNotNull(result.blockingGet().data)

        assertEquals(
            expected = mockAllEmployeesResponse.length,
            actual = actualData.maxSize
        )

        assertEquals(
            expected = EmployeesInteractor.DEFAULT_BATCH_SIZE,
            actual = actualData.batchSize
        )

        assertEquals(
            expected = mockAllEmployeesResponse.records?.size,
            actual = actualData.employees.size
        )
    }

    @Test
    fun `test get employee avatar request headers`(): Unit = runBlocking {
        val result = employeesInteractor.getEmployeeAvatarRequestHeaders()

        assertIs<Single<SuccessResult<List<AvatarRequestHeader>>>>(result)
    }

    @Test
    fun `test get employee avatar request headers mock datastore`(): Unit = runBlocking {
        val sessionId = "sessionId"

        coEvery { mockDataStoreApi.dataStore.sessionId } returns sessionId

        val result = employeesInteractor.getEmployeeAvatarRequestHeaders()

        assertIs<Single<SuccessResult<List<AvatarRequestHeader>>>>(result)
        val actualData = requireNotNull(result.blockingGet().data)

        assertEquals(
            expected = getAvatarRequestHeaders(sessionId),
            actual = actualData
        )
    }

    @Test
    fun `test perform employees search`(): Unit = runBlocking {
        val searchRequest = "searchRequest"

        val result = employeesInteractor.performEmployeesSearch(
            searchRequest = searchRequest
        )

        assertIs<Single<SuccessResult<List<EmployeeBasicInfo>>>>(result)
    }

    @Test
    fun `test perform employees search failed`(): Unit = runBlocking {
        val searchRequest = "searchRequest"

        coEvery {
            mockEmployeesRepositoryApi.employeesRepository.performEmployeesSearch(searchRequest)
        } throws RuntimeException()

        assertFails {
            employeesInteractor.performEmployeesSearch(
                searchRequest = searchRequest
            )
        }
    }

    @Test
    fun `test get employee details`(): Unit = runBlocking {
        val employeeId = 1L

        val result = employeesInteractor.getEmployeeDetails(
            employeeId = employeeId
        )

        assertIs<Single<SuccessResult<EmployeeDetails>>>(result)
    }

    @Test
    fun `test get employee details failed`(): Unit = runBlocking {
        val employeeId = 1L

        coEvery {
            mockEmployeesRepositoryApi.employeesRepository.getEmployeeDetailInfo(employeeId)
        } throws RuntimeException()

        assertFails {
            employeesInteractor.getEmployeeDetails(
                employeeId = employeeId
            )
        }
    }

    @AfterTest
    fun clear() {
        clearAllMocks()
    }
}