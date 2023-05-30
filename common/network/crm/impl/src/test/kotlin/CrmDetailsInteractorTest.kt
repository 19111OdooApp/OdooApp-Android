import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.runBlocking
import odoo.miem.android.common.network.crm.api.entities.details.OpportunityInfo
import odoo.miem.android.common.network.crm.impl.CrmDetailsInteractor
import odoo.miem.android.core.di.impl.ApiRegistry
import odoo.miem.android.core.networkApi.crm.api.di.ICrmRepositoryApi
import odoo.miem.android.core.utils.state.ErrorResult
import odoo.miem.android.core.utils.state.ResultSingle
import org.junit.Test
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertFails
import kotlin.test.assertIs

class CrmDetailsInteractorTest {

    private lateinit var crmDetailsInteractor: CrmDetailsInteractor

    private lateinit var mockCrmRepositoryApi: ICrmRepositoryApi

    @BeforeTest
    fun setup() {
        mockCrmRepositoryApi = mockk(relaxed = true)

        mockkObject(ApiRegistry)
        every { ApiRegistry.getApi(ICrmRepositoryApi::class.java) } returns mockCrmRepositoryApi

        crmDetailsInteractor = CrmDetailsInteractor()
    }

    @Test
    fun `test get opportunity info`(): Unit = runBlocking {
        val opportunityId = 1L

        val result = crmDetailsInteractor.getOpportunityInfo(
            opportunityId = opportunityId
        )

        assertIs<ResultSingle<OpportunityInfo>>(result)
    }

    @Test
    fun `test get opportunity info failed`(): Unit = runBlocking {
        val opportunityId = 1L

        coEvery {
            mockCrmRepositoryApi.crmRepository.getOpportunityInfo(opportunityId)
        } throws RuntimeException()

        assertFails {
            crmDetailsInteractor.getOpportunityInfo(
                opportunityId = opportunityId
            )
        }
    }

    @Test
    fun `test get opportunity catch log notes failed`(): Unit = runBlocking {
        val opportunityId = 1L

        coEvery {
            mockCrmRepositoryApi.crmRepository.getLogNotes(opportunityId)
        } throws RuntimeException()

        val result = crmDetailsInteractor.getOpportunityInfo(
            opportunityId = opportunityId
        )

        assertIs<Single<ErrorResult<Boolean>>>(result)
    }

    @Test
    fun `test get opportunity catch schedule activity failed`(): Unit = runBlocking {
        val opportunityId = 1L

        coEvery {
            mockCrmRepositoryApi.crmRepository.getScheduleActivities(any())
        } throws RuntimeException()

        val result = crmDetailsInteractor.getOpportunityInfo(
            opportunityId = opportunityId
        )

        assertIs<Single<ErrorResult<Boolean>>>(result)
    }

    @Test
    fun `test create log note`(): Unit = runBlocking {
        val opportunityId = 1L
        val text = "text"

        val result = crmDetailsInteractor.createLogNote(
            opportunityId = opportunityId,
            text = text
        )

        assertIs<ResultSingle<Unit>>(result)
    }

    @Test
    fun `test create log note failed`(): Unit = runBlocking {
        val opportunityId = 1L
        val text = "text"

        coEvery {
            mockCrmRepositoryApi.crmRepository.createLogNote(opportunityId, text)
        } throws RuntimeException()

        assertFails {
            crmDetailsInteractor.createLogNote(
                opportunityId = opportunityId,
                text = text
            )
        }
    }

    @AfterTest
    fun clear() {
        clearAllMocks()
    }
}