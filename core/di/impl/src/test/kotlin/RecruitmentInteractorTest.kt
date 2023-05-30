import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.runBlocking
import odoo.miem.android.common.network.recruitment.api.entities.kanban.Status
import odoo.miem.android.common.network.recruitment.impl.RecruitmentInteractor
import odoo.miem.android.core.di.impl.ApiRegistry
import odoo.miem.android.core.networkApi.recruitment.api.di.IRecruitmentRepositoryApi
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentKanbanStagesResponse
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentResponse
import odoo.miem.android.core.utils.state.SuccessResult
import org.junit.Test
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertFails
import kotlin.test.assertIs

class RecruitmentInteractorTest {

    private lateinit var recruitmentInteractor: RecruitmentInteractor

    private lateinit var mockRecruitmentRepositoryApi: IRecruitmentRepositoryApi

    @BeforeTest
    fun setup() {
        mockRecruitmentRepositoryApi = mockk(relaxed = true)

        mockkObject(ApiRegistry)
        every { ApiRegistry.getApi(IRecruitmentRepositoryApi::class.java) } returns mockRecruitmentRepositoryApi

        recruitmentInteractor = RecruitmentInteractor()
    }

    @Test
    fun `test get recruitment kanban info`(): Unit = runBlocking {
        val jobId = 1L

        val result = recruitmentInteractor.getRecruitmentKanbanInfo(
            jobId = jobId
        )

        assertIs<Single<SuccessResult<List<Status>>>>(result)
    }

    @Test
    fun `test get recruitment kanban info repository info failed`(): Unit = runBlocking {
        val jobId = 1L

        coEvery {
            mockRecruitmentRepositoryApi.recruitmentRepository.getRecruitmentKanbanInfo(jobId)
        } throws RuntimeException()

        assertFails {
            recruitmentInteractor.getRecruitmentKanbanInfo(
                jobId = jobId
            )
        }
    }

    @Test
    fun `test get recruitment kanban info repository stages failed`(): Unit = runBlocking {
        val jobId = 1L

        coEvery {
            mockRecruitmentRepositoryApi.recruitmentRepository.getRecruitmentKanbanStages(jobId)
        } throws RuntimeException()

        assertFails {
            recruitmentInteractor.getRecruitmentKanbanInfo(
                jobId = jobId
            )
        }
    }

    @Test
    fun `test get recruitment kanban info check result`(): Unit = runBlocking {
        val jobId = 1L
        val mockRecruitmentResponse = mockk<RecruitmentResponse>(relaxed = true)
        val mockRecruitmentKanbanStagesResponse =
            mockk<RecruitmentKanbanStagesResponse>(relaxed = true)


        coEvery {
            mockRecruitmentRepositoryApi.recruitmentRepository.getRecruitmentKanbanInfo(jobId)
        } returns Single.just(mockRecruitmentResponse)

        coEvery {
            mockRecruitmentRepositoryApi.recruitmentRepository.getRecruitmentKanbanStages(jobId)
        } returns Single.just(mockRecruitmentKanbanStagesResponse)

        val result = recruitmentInteractor.getRecruitmentKanbanInfo(
            jobId = jobId
        )

        assertIs<Single<SuccessResult<List<Status>>>>(result)
        requireNotNull(result.blockingGet().data)
    }

    @Test
    fun `test create new kanban status`(): Unit = runBlocking {
        val jobId = 1L
        val topic = "topic"

        val result = recruitmentInteractor.createNewKanbanStatus(
            jobId = jobId,
            topic = topic
        )

        assertIs<Single<SuccessResult<List<Any>>>>(result)
    }

    @Test
    fun `test create new kanban status failed`(): Unit = runBlocking {
        val jobId = 1L
        val topic = "topic"

        coEvery {
            mockRecruitmentRepositoryApi.recruitmentRepository.createNewKanbanStatus(jobId, topic)
        } throws RuntimeException()

        assertFails {
            recruitmentInteractor.createNewKanbanStatus(
                jobId = jobId,
                topic = topic
            )
        }
    }

    @Test
    fun `test change stage in kanban`(): Unit = runBlocking {
        val stageId = 1L
        val employeeId = 1L

        val result = recruitmentInteractor.changeStageInRecruitmentKanban(
            stageId = stageId,
            employeeId = employeeId,
        )

        assertIs<Single<SuccessResult<Boolean>>>(result)
    }

    @Test
    fun `test change stage in kanban failed`(): Unit = runBlocking {
        val stageId = 1L
        val employeeId = 1L

        coEvery {
            mockRecruitmentRepositoryApi.recruitmentRepository.changeStageInRecruitmentKanban(
                stageId = stageId,
                employeeId = employeeId,
            )
        } throws RuntimeException()

        assertFails {
            recruitmentInteractor.changeStageInRecruitmentKanban(
                stageId = stageId,
                employeeId = employeeId,
            )
        }
    }

    @AfterTest
    fun clear() {
        clearAllMocks()
    }
}