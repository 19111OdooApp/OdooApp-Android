
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.runBlocking
import odoo.miem.android.common.network.crm.api.entities.kanban.StatusCRM
import odoo.miem.android.common.network.crm.impl.CrmInteractor
import odoo.miem.android.core.di.impl.ApiRegistry
import odoo.miem.android.core.networkApi.crm.api.di.ICrmRepositoryApi
import odoo.miem.android.core.utils.state.ResultSingle
import org.junit.Test
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertFails
import kotlin.test.assertIs

class CrmInteractorTest {

    private lateinit var сrmInteractor: CrmInteractor

    private lateinit var mockCrmRepositoryApi: ICrmRepositoryApi

    @BeforeTest
    fun setup() {
        mockCrmRepositoryApi = mockk(relaxed = true)

        mockkObject(ApiRegistry)
        every { ApiRegistry.getApi(ICrmRepositoryApi::class.java) } returns mockCrmRepositoryApi

        сrmInteractor = CrmInteractor()
    }

    @Test
    fun `test get crm kanban info`(): Unit = runBlocking {
        val userId = 1

        val result = сrmInteractor.getCrmKanbanInfo(
            userId = userId
        )

        assertIs<ResultSingle<List<StatusCRM>>>(result)
    }

    @Test
    fun `test get crm kanban repository info failed`(): Unit = runBlocking {
        val userId = 1

        coEvery {
            mockCrmRepositoryApi.crmRepository.getCrmKanbanInfo(userId)
        } throws RuntimeException()

        assertFails {
            сrmInteractor.getCrmKanbanInfo(
                userId = userId
            )
        }
    }

    @Test
    fun `test get crm kanban repository stages failed`(): Unit = runBlocking {
        val userId = 1

        coEvery {
            mockCrmRepositoryApi.crmRepository.getCrmKanbanStages(userId)
        } throws RuntimeException()

        assertFails {
            сrmInteractor.getCrmKanbanInfo(
                userId = userId
            )
        }
    }

    @Test
    fun `test get crm kanban info proceed result`(): Unit = runBlocking {
        val userId = 1

        coEvery {
            mockCrmRepositoryApi.crmRepository.getCrmKanbanInfo(userId)
        } returns Single.just(CrmTestData.crmResponse)

        coEvery {
            mockCrmRepositoryApi.crmRepository.getCrmKanbanStages(userId)
        } returns Single.just(CrmTestData.crmKanbanStagesResponse)

        val result = сrmInteractor.getCrmKanbanInfo(
            userId = userId
        )

        assertIs<ResultSingle<List<StatusCRM>>>(result)
        requireNotNull(result.blockingGet().data)
    }

    @Test
    fun `test create new crm status`(): Unit = runBlocking {
        val topic = "topic"

        val result = сrmInteractor.createNewCrmStatus(
            topic = topic
        )

        assertIs<ResultSingle<List<Any>>>(result)
    }

    @Test
    fun `test create new crm status failed`(): Unit = runBlocking {
        val topic = "topic"

        coEvery {
            mockCrmRepositoryApi.crmRepository.createNewCrmStatus(topic)
        } throws RuntimeException()

        assertFails {
            сrmInteractor.createNewCrmStatus(
                topic = topic
            )
        }
    }

    @Test
    fun `test change stage kanban`(): Unit = runBlocking {
        val stageId = 1L
        val opportunityId = 1L

        val result = сrmInteractor.changeStageInCrmKanban(
            stageId = stageId,
            opportunityId = opportunityId
        )

        assertIs<ResultSingle<Boolean>>(result)
    }

    @Test
    fun `test change stage failed`(): Unit = runBlocking {
        val stageId = 1L
        val opportunityId = 1L

        coEvery {
            mockCrmRepositoryApi.crmRepository.changeStageInCrmKanban(stageId, opportunityId)
        } throws RuntimeException()

        assertFails {
            сrmInteractor.changeStageInCrmKanban(
                stageId = stageId,
                opportunityId = opportunityId
            )
        }
    }


    @AfterTest
    fun clear() {
        clearAllMocks()
    }
}