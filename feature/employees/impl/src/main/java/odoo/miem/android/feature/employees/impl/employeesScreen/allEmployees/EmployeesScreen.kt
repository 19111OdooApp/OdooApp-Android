package odoo.miem.android.feature.employees.impl.employeesScreen.allEmployees

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import odoo.miem.android.common.network.employees.api.entities.EmployeeAvatarRequestHeaders
import odoo.miem.android.common.network.employees.api.entities.EmployeeBasicInfo
import odoo.miem.android.common.uiKitComponents.progressbar.LoadingScreen
import odoo.miem.android.common.uiKitComponents.screen.searchLike.SearchLikeScreen
import odoo.miem.android.common.uiKitComponents.stateholder.StateHolder
import odoo.miem.android.common.uiKitComponents.stateholder.error.ErrorScreen
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.core.utils.rx.collectAsState
import odoo.miem.android.core.utils.state.LoadingResult
import odoo.miem.android.feature.employees.api.IEmployeesScreen
import odoo.miem.android.feature.employees.impl.EmployeesViewModel
import odoo.miem.android.feature.employees.impl.R
import odoo.miem.android.feature.employees.impl.employeesScreen.allEmployees.components.EmployeesList
import odoo.miem.android.feature.navigation.api.data.Routes
import javax.inject.Inject

/**
 * [EmployeesScreen] implementation of [IEmployeesScreen]
 *
 * Methods by purpose:
 * - [EmployeesScreen] - the input point to this screen is needed for initial initialization.
 * For example, ViewModel initialization
 * - [EmployeesScreenContent] - directly layout of this screen
 * - [EmployeesScreenPreview] - preview of the layout that turned out in [EmployeesScreenContent]
 *
 * @author Egor Danilov
 */
class EmployeesScreen @Inject constructor() : IEmployeesScreen {

    @SuppressLint("NotConstructor")
    @Composable
    override fun EmployeesScreen(
        viewModelStoreOwner: ViewModelStoreOwner,
        navController: NavHostController,
        showMessage: (Int) -> Unit
    ) {
        val viewModel: EmployeesViewModel = viewModel(viewModelStoreOwner)

        val userInfoState by viewModel.userInfoState.collectAsState()

        val allEmployeesState by viewModel.allEmployeesState.collectAsState()
        val employeeSearchState by viewModel.employeesSearchState.collectAsState()

        val avatarRequestHeadersState by viewModel.avatarRequestHeaders.collectAsState()

        val allEmployeesItems = viewModel.allEmployeesList
        val filteredEmployees = viewModel.filteredEmployeesList

        LaunchedEffect(Unit) {
            viewModel.onEmployeesOpen()
        }

        StateHolder(
            state = allEmployeesState,
            loadingContent = { LoadingScreen() },
            errorContent = {
                ErrorScreen(
                    isSessionExpired = it.isSessionExpired,
                    onSessionExpired = { navController.navigate(Routes.authorization) },
                    onRetry = { viewModel.onEmployeesOpen() }
                )
            },
            successContent = {
                EmployeesScreenContent(
                    userName = userInfoState.data?.name ?: "Cool user",
                    allEmployees = allEmployeesItems,
                    filteredEmployees = filteredEmployees,
                    employeeAvatarRequestHeaders = avatarRequestHeadersState.data ?: emptyList(),
                    isSearchLoading = employeeSearchState is LoadingResult,
                    performSearch = {
                        viewModel.performEmployeeSearch(it)
                    },
                    onEmployeeClick = {
                        navController.navigate("${Routes.employeeDetails}/${it.id}")
                    },
                    onReachBottom = {
                        if (!viewModel.isSessionExpired) {
                            viewModel.getAllEmployees()
                        }
                    },
                    onNavigateToModulesPressed = {
                        navController.navigate(Routes.selectingModules)
                    },
                    onUserIconClick = {
                        navController.navigate(Routes.userProfile)
                    },
                    onBackPressed = navController::popBackStack
                )
            }
        )
    }

    @Composable
    private fun EmployeesScreenContent(
        userName: String,
        allEmployees: List<EmployeeBasicInfo> = emptyList(),
        filteredEmployees: List<EmployeeBasicInfo> = emptyList(),
        employeeAvatarRequestHeaders: List<EmployeeAvatarRequestHeaders> = emptyList(),
        isSearchLoading: Boolean = false,
        performSearch: (String) -> Unit = {},
        onEmployeeClick: (EmployeeBasicInfo) -> Unit = {},
        onReachBottom: () -> Unit = {},
        onNavigateToModulesPressed: () -> Unit = {},
        onUserIconClick: () -> Unit = {},
        onBackPressed: () -> Unit = {}
    ) {
        val content: @Composable (ColumnScope.(items: List<EmployeeBasicInfo>) -> Unit) = { employees ->
            EmployeesList(
                employees = employees,
                employeeAvatarRequestHeaders = employeeAvatarRequestHeaders,
                onClick = onEmployeeClick,
                onReachBottom = onReachBottom,
            )
        }

        BackHandler(enabled = true) {
            onBackPressed()
        }

        SearchLikeScreen(
            items = allEmployees,
            filteredItems = filteredEmployees,
            userName = userName,
            searchBarPlaceholder = R.string.employees_search_bar_placeholder,
            mainTitle = stringResource(R.string.employees_main_title),
            mainListContent = content,
            searchResultListContent = content,
            onNavigateToModulesPressed = onNavigateToModulesPressed,
            onUserIconClick = onUserIconClick,
            performSearch = performSearch,
            isSearchLoading = isSearchLoading
        )
    }

    @Composable
    @Preview(showBackground = true, backgroundColor = 0xFFFFFF)
    private fun EmployeesScreenPreview() = OdooMiemAndroidTheme {
        EmployeesScreenContent(
            "Cool user",
            allEmployees = listOf(
                EmployeeBasicInfo(
                    id = 0L,
                    name = "Arina Shoshina",
                    job = "Consultant",
                    email = "abigail.peterson39@example.com",
                    phone = "(482)-233-3393",
                    avatarLink = null
                )
            )
        )
    }
}
