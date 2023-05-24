package odoo.miem.android.feature.employees.impl.employeesScreen.employeeDetails

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import odoo.miem.android.common.uiKitComponents.progressbar.LoadingScreen
import odoo.miem.android.common.uiKitComponents.screen.base.detailsLike.components.DetailedInfoType
import odoo.miem.android.common.uiKitComponents.screen.base.detailsLike.components.PagesType
import odoo.miem.android.common.uiKitComponents.screen.base.detailsLike.components.TextType
import odoo.miem.android.common.uiKitComponents.screen.employeesLike.employeeDetailsLike.EmployeeDetailsLikeScreen
import odoo.miem.android.common.uiKitComponents.screen.employeesLike.employeeDetailsLike.models.EmployeeDetailsHeader
import odoo.miem.android.common.uiKitComponents.stateholder.StateHolder
import odoo.miem.android.common.uiKitComponents.stateholder.error.ErrorScreen
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.core.utils.rx.collectAsState
import odoo.miem.android.feature.employees.api.IEmployeeDetailsScreen
import odoo.miem.android.feature.employees.impl.EmployeesViewModel
import odoo.miem.android.feature.employees.impl.employeesScreen.employeeDetails.helpers.getDetailsHeader
import odoo.miem.android.feature.employees.impl.employeesScreen.employeeDetails.helpers.getDetailsPages
import odoo.miem.android.feature.navigation.api.data.Routes
import timber.log.Timber
import javax.inject.Inject

class EmployeeDetailsScreen @Inject constructor() : IEmployeeDetailsScreen {

    @SuppressLint("NotConstructor")
    @Composable
    override fun EmployeeDetailsScreen(
        employeeId: Long,
        viewModelStoreOwner: ViewModelStoreOwner,
        navController: NavHostController,
        showMessage: (Int) -> Unit
    ) {
        val viewModel: EmployeesViewModel = viewModel(viewModelStoreOwner)
        val context = LocalContext.current

        Timber.d("EmployeeDetailsScreen(): employeeId = $employeeId")

        val employeeDetailsState by viewModel.employeeDetails.collectAsState()

        val avatarRequestHeadersState by viewModel.avatarRequestHeaders.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.onEmployeeDetailsOpen(employeeId)
        }

        StateHolder(
            state = employeeDetailsState,
            loadingContent = { LoadingScreen() },
            errorContent = {
                ErrorScreen(
                    isSessionExpired = it.isSessionExpired,
                    onSessionExpired = { navController.navigate(Routes.authorization) },
                    onRetry = { viewModel.getEmployeeDetails(employeeId) }
                )
            },
            successContent = { employeeDetails ->
                employeeDetails.data?.let {
                    EmployeeDetailsScreenContent(
                        header = getDetailsHeader(
                            employeeDetails = it,
                            avatarRequestHeaders = avatarRequestHeadersState.data ?: emptyList()
                        ),
                        pages = getDetailsPages(
                            stringResolver = { res -> context.resources.getString(res) },
                            employeeDetails = it
                        ),
                        navigateBack = navController::popBackStack
                    )
                } ?: ErrorScreen(
                    isSessionExpired = false,
                    onSessionExpired = { navController.navigate(Routes.authorization) },
                    onRetry = { viewModel.getEmployeeDetails(employeeId) }
                )
            }
        )
    }

    @Composable
    private fun EmployeeDetailsScreenContent(
        header: EmployeeDetailsHeader,
        pages: List<PagesType> = emptyList(),
        navigateBack: () -> Unit = {}
    ) = EmployeeDetailsLikeScreen(
        header = header,
        pages = pages,
        navigateBack = navigateBack
    )

    @Composable
    @Preview(showBackground = true, backgroundColor = 0xFFFFFF)
    private fun EmployeesDetailsScreenPreview() = OdooMiemAndroidTheme {
        EmployeeDetailsScreenContent(
            header = EmployeeDetailsHeader(
                id = -1L,
                name = "Arina Shoshina",
                email = "aashoshina@miem.hse.ru",
                mobilePhone = "+79013686745",
                workPhone = "+790212121",
                company = "MIEM",
                avatarLink = "https://crm.auditory.ru/web/image?model=hr.employee&id=1608&field=avatar_128",
                avatarRequestHeaders = emptyList()
            ),
            pages = listOf(
                DetailedInfoType(
                    blocks = mapOf(
                        "Main Information" to listOf(
                            DetailedInfoType.TextType(
                                key = "Department",
                                text = "Professional services"
                            ),
                            DetailedInfoType.TextType(
                                key = "Studygroup",
                                text = null
                            ),
                            DetailedInfoType.TextType(
                                key = "Manager",
                                text = null
                            ),
                            DetailedInfoType.TextType(
                                key = "Coach",
                                text = null
                            ),
                        ),
                        "Work Information" to listOf(
                            DetailedInfoType.TextType(
                                key = "Work Address",
                                text = "My Company (San Francisco)\n" +
                                    "250 Executive Park Blvd, Suite 3400\n" +
                                    "San Francisco CA 94134\n" +
                                    "United States"
                            ),
                            DetailedInfoType.TextType(
                                key = "Working Hours",
                                text = "40 hours"
                            ),
                            DetailedInfoType.TextType(
                                key = "Timezone",
                                text = "Europe/Moscow"
                            )
                        )
                    )
                ),
                TextType(
                    topic = "About me",
                    text = "Some cool application summary"
                )
            ),
        )
    }
}
