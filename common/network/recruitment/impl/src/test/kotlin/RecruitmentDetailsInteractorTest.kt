import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.runBlocking
import odoo.miem.android.common.network.recruitment.api.entities.details.ApplicationInfo
import odoo.miem.android.common.network.recruitment.impl.RecruitmentDetailsInteractor
import odoo.miem.android.core.di.impl.ApiRegistry
import odoo.miem.android.core.networkApi.recruitment.api.di.IRecruitmentRepositoryApi
import odoo.miem.android.core.utils.state.ErrorResult
import odoo.miem.android.core.utils.state.SuccessResult
import org.junit.Test
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertFails
import kotlin.test.assertIs

class RecruitmentDetailsInteractorTest {

    private lateinit var recruitmentDetailsInteractor: RecruitmentDetailsInteractor

    private lateinit var mockRecruitmentRepositoryApi: IRecruitmentRepositoryApi

    @BeforeTest
    fun setup() {
        mockRecruitmentRepositoryApi = mockk(relaxed = true)

        mockkObject(ApiRegistry)
        every { ApiRegistry.getApi(IRecruitmentRepositoryApi::class.java) } returns mockRecruitmentRepositoryApi

        recruitmentDetailsInteractor = RecruitmentDetailsInteractor()
    }

    @Test
    fun `test get application info`(): Unit = runBlocking {
        val applicationId = 1L

        val result = recruitmentDetailsInteractor.getApplicationInfo(
            applicationId = applicationId
        )

        assertIs<Single<SuccessResult<ApplicationInfo>>>(result)
    }

    @Test
    fun `test get application info failed`(): Unit = runBlocking {
        val applicationId = 1L

        coEvery {
            mockRecruitmentRepositoryApi.recruitmentRepository.getApplicationInfo(applicationId)
        } throws RuntimeException()

        assertFails {
            recruitmentDetailsInteractor.getApplicationInfo(
                applicationId = applicationId
            )
        }
    }

    @Test
    fun `test get application info catch log note failed`(): Unit = runBlocking {
        val applicationId = 1L

        coEvery {
            mockRecruitmentRepositoryApi.recruitmentRepository.getLogNotes(any())
        } throws RuntimeException()

        val result = recruitmentDetailsInteractor.getApplicationInfo(
            applicationId = applicationId
        )

        assertIs<Single<ErrorResult<ApplicationInfo>>>(result)
    }

    @Test
    fun `test get application info catch schedule activity failed`(): Unit = runBlocking {
        val applicationId = 1L

        coEvery {
            mockRecruitmentRepositoryApi.recruitmentRepository.getScheduleActivities(any())
        } throws RuntimeException()

        val result = recruitmentDetailsInteractor.getApplicationInfo(
            applicationId = applicationId
        )

        assertIs<Single<ErrorResult<ApplicationInfo>>>(result)
    }

    @Test
    fun `test create log note`(): Unit = runBlocking {
        val userId = 1L
        val text = "text"

        val result = recruitmentDetailsInteractor.createLogNote(
            userId = userId,
            text = text
        )

        assertIs<Single<SuccessResult<Unit>>>(result)
    }

    @Test
    fun `test create log note failed`(): Unit = runBlocking {
        val userId = 1L
        val text = "text"

        coEvery {
            mockRecruitmentRepositoryApi.recruitmentRepository.createLogNote(any(), any())
        } throws RuntimeException()

        assertFails {
            recruitmentDetailsInteractor.createLogNote(
                userId = userId,
                text = text
            )
        }
    }

    @AfterTest
    fun clear() {
        clearAllMocks()
    }
}