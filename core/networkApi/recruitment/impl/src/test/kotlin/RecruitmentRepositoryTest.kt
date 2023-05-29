import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.runBlocking
import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.di.impl.ApiRegistry
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentApplicationDetailsResponse
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentJobsResponse
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentKanbanStagesResponse
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentLogNoteResponse
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentResponse
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentScheduleActivityResponse
import odoo.miem.android.core.networkApi.recruitment.impl.RecruitmentRepository
import org.junit.Test
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertIs

class RecruitmentRepositoryTest {

    private lateinit var recruitmentRepository: RecruitmentRepository


    @BeforeTest
    fun setup() {
        val mockDatastore = mockk<IDataStoreApi>(relaxed = true)

        mockkObject(ApiRegistry)
        every { ApiRegistry.getApi<IDataStoreApi>(any()) } returns mockDatastore

        recruitmentRepository = RecruitmentRepository()
    }

    @Test
    fun `test get recruitment kanban info`(): Unit = runBlocking {
        val jobId = 1L

        val result = recruitmentRepository.getRecruitmentKanbanInfo(
            jobId = jobId
        )

        assertIs<Single<RecruitmentResponse>>(result)
    }

    @Test
    fun `test get recruitment kanban stages`(): Unit = runBlocking {
        val jobId = 1L

        val result = recruitmentRepository.getRecruitmentKanbanStages(
            jobId = jobId
        )

        assertIs<Single<RecruitmentKanbanStagesResponse>>(result)
    }

    @Test
    fun `test create new kanban status`(): Unit = runBlocking {
        val jobId = 1L
        val topic = "topic"

        val result = recruitmentRepository.createNewKanbanStatus(
            jobId = jobId,
            topic = topic
        )

        assertIs<Single<List<Any>>>(result)
    }

    @Test
    fun `test change stage in kanban`(): Unit = runBlocking {
        val stageId = 1L
        val employeeId = 1L

        val result = recruitmentRepository.changeStageInRecruitmentKanban(
            stageId = stageId,
            employeeId = employeeId
        )

        assertIs<Single<Boolean>>(result)
    }

    @Test
    fun `test set job publication`(): Unit = runBlocking {
        val jobId = 1L

        val result1 = recruitmentRepository.setJobPublication(
            jobId = jobId,
            publish = true
        )

        assertIs<Single<Boolean>>(result1)

        val result2 = recruitmentRepository.setJobPublication(
            jobId = jobId,
            publish = false
        )

        assertIs<Single<Boolean>>(result2)
    }

    @Test
    fun `test set job favoritable`(): Unit = runBlocking {
        val jobId = 1L

        val result1 = recruitmentRepository.setJobFavoritable(
            jobId = jobId,
            isFavorite = true
        )

        assertIs<Single<Boolean>>(result1)

        val result2 = recruitmentRepository.setJobFavoritable(
            jobId = jobId,
            isFavorite = false
        )

        assertIs<Single<Boolean>>(result2)
    }

    @Test
    fun `test setting job publication`(): Unit = runBlocking {
        val jobId = 1L

        val result1 = recruitmentRepository.setJobPublication(
            jobId = jobId,
            publish = true
        )

        assertIs<Single<Boolean>>(result1)

        val result2 = recruitmentRepository.setJobPublication(
            jobId = jobId,
            publish = false
        )

        assertIs<Single<Boolean>>(result2)
    }

    @Test
    fun `test setting job favoritable`(): Unit = runBlocking {
        val jobId = 1L

        val result1 = recruitmentRepository.setJobFavoritable(
            jobId = jobId,
            isFavorite = true
        )

        assertIs<Single<Boolean>>(result1)

        val result2 = recruitmentRepository.setJobFavoritable(
            jobId = jobId,
            isFavorite = false
        )

        assertIs<Single<Boolean>>(result2)
    }


    @Test
    fun `test get jobs info`(): Unit = runBlocking {
        val result = recruitmentRepository.getRecruitmentJobsInfo()

        assertIs<Single<RecruitmentJobsResponse>>(result)
    }

    @Test
    fun `test setting job recruit`(): Unit = runBlocking {
        val jobId = 1L

        val result1 = recruitmentRepository.setJobRecruit(
            jobId = jobId,
            isRecruitingDone = true
        )

        assertIs<Single<Boolean>>(result1)

        val result2 = recruitmentRepository.setJobRecruit(
            jobId = jobId,
            isRecruitingDone = false
        )

        assertIs<Single<Boolean>>(result2)
    }

    @Test
    fun `test get application info`(): Unit = runBlocking {
        val applicationId = 1L

        val result = recruitmentRepository.getApplicationInfo(
            applicationId = applicationId
        )

        assertIs<Single<RecruitmentApplicationDetailsResponse>>(result)
    }

    @Test
    fun `test get log notes`(): Unit = runBlocking {
        val userId = 1L

        val result = recruitmentRepository.getLogNotes(
            userId = userId
        )

        assertIs<Single<List<RecruitmentLogNoteResponse>>>(result)
    }

    @Test
    fun `test creating log note`(): Unit = runBlocking {
        val userId = 1L
        val text = "text"

        val result = recruitmentRepository.createLogNote(
            userId = userId,
            text = text
        )

        assertIs<Single<Unit>>(result)
    }

    @Test
    fun `test get schedule activities`(): Unit = runBlocking {
        val activityIds = listOf(1L, 2L, 3L)

        val result = recruitmentRepository.getScheduleActivities(
            activityIds = activityIds,
        )

        assertIs<Single<List<RecruitmentScheduleActivityResponse>>>(result)
    }


    @AfterTest
    fun clear() {
        clearAllMocks()
    }
}