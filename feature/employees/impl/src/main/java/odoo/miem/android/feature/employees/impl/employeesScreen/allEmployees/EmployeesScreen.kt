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
import odoo.miem.android.common.network.employees.api.entities.EmployeeBasicInfo
import odoo.miem.android.common.uiKitComponents.progressbar.LoadingScreen
import odoo.miem.android.common.uiKitComponents.screen.base.searchLike.model.ScreenPage
import odoo.miem.android.common.uiKitComponents.screenTemplates.base.searchLike.SearchLikeScreenWithPages
import odoo.miem.android.common.uiKitComponents.stateholder.StateHolder
import odoo.miem.android.common.uiKitComponents.stateholder.error.ErrorScreen
import odoo.miem.android.common.utils.avatar.AvatarRequestHeader
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.core.utils.rx.collectAsState
import odoo.miem.android.core.utils.state.LoadingResult
import odoo.miem.android.feature.employees.api.IEmployeesScreen
import odoo.miem.android.feature.employees.impl.EmployeesViewModel
import odoo.miem.android.feature.employees.impl.R
import odoo.miem.android.feature.employees.impl.employeesScreen.allEmployees.components.EmployeesList
import odoo.miem.android.feature.navigation.api.data.Routes
import odoo.miem.android.feature.navigation.api.utils.navigateToUserProfile
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
                    pageWithEmployees = allEmployeesState.data ?: DEFAULT_SCREEN_PAGE,
                    filteredEmployees = filteredEmployees,
                    avatarRequestHeaders = avatarRequestHeadersState.data ?: emptyList(),
                    isSearchLoading = employeeSearchState is LoadingResult,
                    performSearch = {
                        viewModel.performEmployeeSearch(it)
                    },
                    onSearchBarValueChange = {
                        viewModel.filteredEmployeesList.clear()
                    },
                    onEmployeeClick = {
                        navController.navigate("${Routes.employeeDetails}/${it.id}")
                    },
                    onChangePageClick = { newPage ->
                        if (!viewModel.isSessionExpired) {
                            viewModel.getAllEmployees(newPage)
                        }
                    },
                    onNavigateToModulesPressed = {
                        navController.navigate(Routes.selectingModules)
                    },
                    onUserIconClick = {
                        navController.navigateToUserProfile()
                    },
                    onBackPressed = {
                        viewModel.onEmployeesClosed()
                        navController.popBackStack()
                    }
                )
            }
        )
    }

    @Composable
    private fun EmployeesScreenContent(
        userName: String,
        pageWithEmployees: ScreenPage<EmployeeBasicInfo>,
        filteredEmployees: List<EmployeeBasicInfo> = emptyList(),
        avatarRequestHeaders: List<AvatarRequestHeader> = emptyList(),
        isSearchLoading: Boolean = false,
        performSearch: (String) -> Unit = {},
        onSearchBarValueChange: () -> Unit = {},
        onEmployeeClick: (EmployeeBasicInfo) -> Unit = {},
        onChangePageClick: (newPage: Int) -> Unit = {},
        onNavigateToModulesPressed: () -> Unit = {},
        onUserIconClick: () -> Unit = {},
        onBackPressed: () -> Unit = {}
    ) {
        val content: @Composable (ColumnScope.(items: ScreenPage<EmployeeBasicInfo>) -> Unit) =
            { employees ->
                EmployeesList(
                    employeesPage = employees,
                    avatarRequestHeaders = avatarRequestHeaders,
                    onEmployeeClick = onEmployeeClick,
                    onChangePageClick = onChangePageClick,
                )
            }

        BackHandler(enabled = true) {
            onBackPressed()
        }

        SearchLikeScreenWithPages(
            screenPage = pageWithEmployees,
            filteredItems = filteredEmployees,
            userName = userName,
            searchBarPlaceholder = R.string.employees_search_bar_placeholder,
            mainTitle = stringResource(R.string.employees_main_title),
            mainListContent = content,
            searchResultListContent = content,
            onNavigateToModulesPressed = onNavigateToModulesPressed,
            onUserIconClick = onUserIconClick,
            performSearch = performSearch,
            onSearchBarValueChange = onSearchBarValueChange,
            isSearchLoading = isSearchLoading
        )
    }

    private companion object {
        val DEFAULT_SCREEN_PAGE = ScreenPage<EmployeeBasicInfo>(items = emptyList())
    }

    @Composable
    @Preview(showBackground = true, backgroundColor = 0xFFFFFF)
    private fun EmployeesScreenPreview() = OdooMiemAndroidTheme {
        EmployeesScreenContent(
            "Cool user",
            pageWithEmployees = ScreenPage(
                maxSize = 250,
                currentPage = 0,
                pageSize = 30,
                fromIndex = 0,
                toIndex = 30,
                items = listOf(
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
        )
    }
}
