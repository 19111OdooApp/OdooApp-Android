import io.mockk.clearAllMocks
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.runBlocking
import odoo.miem.android.core.networkApi.crm.api.entities.CrmApplicationDetailsResponse
import odoo.miem.android.core.networkApi.crm.api.entities.CrmKanbanStagesResponse
import odoo.miem.android.core.networkApi.crm.api.entities.CrmResponse
import odoo.miem.android.core.networkApi.crm.api.entities.CrmScheduleActivityResponse
import odoo.miem.android.core.networkApi.crm.impl.CrmRepository
import org.junit.Test
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertIs

class CrmRepositoryTest {

    private lateinit var crmRepositoryTest: CrmRepository

    @BeforeTest
    fun setup() {
        crmRepositoryTest = CrmRepository()
    }

    @Test
    fun `test get kanban info`(): Unit = runBlocking {
        val userId = 0

        val result = crmRepositoryTest.getCrmKanbanInfo(
            userId = userId
        )

        assertIs<Single<CrmResponse>>(result)
    }

    @Test
    fun `test get kanban stages`(): Unit = runBlocking {
        val userId = 0

        val result = crmRepositoryTest.getCrmKanbanStages(
            userId = userId
        )

        assertIs<Single<CrmKanbanStagesResponse>>(result)
    }

    @Test
    fun `test change in kanban`(): Unit = runBlocking {
        val stageId = 0L
        val opportunityId = 0L

        val result = crmRepositoryTest.changeStageInCrmKanban(
            stageId = stageId,
            opportunityId = opportunityId
        )

        assertIs<Single<Boolean>>(result)
    }

    @Test
    fun `test get opportunity info`(): Unit = runBlocking {
        val opportunityId = 0L

        val result = crmRepositoryTest.getOpportunityInfo(
            opportunityId = opportunityId
        )

        assertIs<Single<CrmApplicationDetailsResponse>>(result)
    }

    @Test
    fun `test get log notes`(): Unit = runBlocking {
        val opportunityId = 0L

        val result = crmRepositoryTest.getLogNotes(
            opportunityId = opportunityId
        )

        assertIs<Single<CrmApplicationDetailsResponse>>(result)
    }

    @Test
    fun `test create log note`(): Unit = runBlocking {
        val opportunityId = 0L
        val text = "text"

        val result = crmRepositoryTest.createLogNote(
            opportunityId = opportunityId,
            text = text
        )

        assertIs<Single<Unit>>(result)
    }

    @Test
    fun `test get schedule activities`(): Unit = runBlocking {
        val activityIds = listOf(1L, 2L, 3L)

        val result = crmRepositoryTest.getScheduleActivities(
            activityIds = activityIds
        )

        assertIs<Single<List<CrmScheduleActivityResponse>>>(result)
    }

    @AfterTest
    fun clear() {
        clearAllMocks()
    }
}