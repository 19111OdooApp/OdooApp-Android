package odoo.miem.android.feature.navigation.impl.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kotlinx.coroutines.launch
import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.feature.authorization.base.api.IAuthorizationScreen
import odoo.miem.android.feature.authorization.base.api.di.IAuthorizationApi
import odoo.miem.android.feature.crm.api.ICrmScreen
import odoo.miem.android.feature.crm.api.di.ICrmApi
import odoo.miem.android.feature.employees.api.IEmployeeDetailsScreen
import odoo.miem.android.feature.employees.api.IEmployeesScreen
import odoo.miem.android.feature.employees.api.di.IEmployeesApi
import odoo.miem.android.feature.moduleNotFound.api.IModuleNotFoundScreen
import odoo.miem.android.feature.moduleNotFound.api.di.IModuleNotFoundApi
import odoo.miem.android.feature.navigation.api.data.Routes
import odoo.miem.android.feature.recruitment.api.IRecruitmentDetailsScreen
import odoo.miem.android.feature.recruitment.api.IRecruitmentJobsScreen
import odoo.miem.android.feature.recruitment.api.IRecruitmentKanbanScreen
import odoo.miem.android.feature.recruitment.api.di.IRecruitmentApi
import odoo.miem.android.feature.selectingModules.api.ISelectingModulesScreen
import odoo.miem.android.feature.selectingModules.api.di.ISelectingModulesApi
import odoo.miem.android.feature.userProfile.api.IUserProfileScreen
import odoo.miem.android.feature.userProfile.api.di.IUserProfileScreenApi

/**
 * [Navigation] composable function, which is needed to initialize navigation across screens
 *
 * For adding new screen one should write corresponding path to [Routes]
 * and add new [composable] в [NavHost]
 *
 * @author Vorozhtsov Mikhail
 */
@Composable
fun Navigation(
    snackbarHostState: SnackbarHostState,
    paddingValues: PaddingValues,
    navController: NavHostController
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val showMessage: (Int) -> Unit = { message ->
        val strMessage = context.getString(message)
        scope.launch {
            snackbarHostState.showSnackbar(strMessage)
        }
    }

    // Screens
    val authorizationScreen by api(IAuthorizationApi::authorizationScreen)
    val selectingModulesScreen by api(ISelectingModulesApi::selectingModulesScreen)
    val moduleNotFoundScreen by api(IModuleNotFoundApi::moduleNotFoundScreen)
    val recruitmentScreen by api(IRecruitmentApi::recruitmentScreen)
    val recruitmentDetailsScreen by api(IRecruitmentApi::recruitmentDetailsScreen)
    val recruitmentJobsScreen by api(IRecruitmentApi::recruitmentJobsScreen)
    val crmScreen by api(ICrmApi::crmScreen)
    val userProfileScreen by api(IUserProfileScreenApi::userProfileScreen)
    val employeesScreen by api(IEmployeesApi::employeesScreen)
    val employeeDetailsScreen by api(IEmployeesApi::employeeDetailsScreen)
    val dataStore by api(IDataStoreApi::dataStore)

    NavigationContent(
        authorizationScreen = authorizationScreen,
        selectingModulesScreen = selectingModulesScreen,
        moduleNotFoundScreen = moduleNotFoundScreen,
        recruitmentScreen = recruitmentScreen,
        recruitmentDetailsScreen = recruitmentDetailsScreen,
        recruitmentJobsScreen = recruitmentJobsScreen,
        crmScreen = crmScreen,
        userProfileScreen = userProfileScreen,
        employeesScreen = employeesScreen,
        employeeDetailsScreen = employeeDetailsScreen,
        paddingValues = paddingValues,
        navController = navController,
        isAuthorized = dataStore.isAuthorized,
        showMessage = showMessage,
    )
}

@Composable
fun NavigationContent(
    authorizationScreen: IAuthorizationScreen,
    selectingModulesScreen: ISelectingModulesScreen,
    moduleNotFoundScreen: IModuleNotFoundScreen,
    recruitmentScreen: IRecruitmentKanbanScreen,
    recruitmentDetailsScreen: IRecruitmentDetailsScreen,
    recruitmentJobsScreen: IRecruitmentJobsScreen,
    crmScreen: ICrmScreen,
    userProfileScreen: IUserProfileScreen,
    employeesScreen: IEmployeesScreen,
    employeeDetailsScreen: IEmployeeDetailsScreen,
    paddingValues: PaddingValues,
    navController: NavHostController,
    isAuthorized: Boolean,
    showMessage: (Int) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = remember {
                if (isAuthorized) {
                    Routes.selectingModules
                } else {
                    Routes.authorization
                }
            }
        ) {
            composable(Routes.authorization) {
                authorizationScreen.AuthorizationScreen(
                    navController = navController,
                    showMessage = showMessage
                )
            }

            composable(Routes.selectingModules) {
                selectingModulesScreen.SelectingModulesScreen(
                    navController = navController,
                    showMessage = showMessage
                )
            }

            composable(Routes.moduleNotFound) {
                moduleNotFoundScreen.ModuleNotFoundScreen(
                    navController = navController
                )
            }

            composable(
                "${Routes.recruitmentKanban}/{${Routes.Arguments.recruitmentJobId}}",
                arguments = listOf(
                    navArgument(Routes.Arguments.recruitmentJobId) { type = NavType.LongType }
                )
            ) {
                recruitmentScreen.RecruitmentKanbanScreen(
                    navController = navController,
                    jobId = it.arguments!!.getLong(Routes.Arguments.recruitmentJobId),
                    showMessage = showMessage
                )
            }

            composable(
                "${Routes.recruitmentDetails}/{${Routes.Arguments.recruitmentApplicationId}}",
                arguments = listOf(
                    navArgument(Routes.Arguments.recruitmentApplicationId) {
                        type = NavType.LongType
                    }
                )
            ) {
                recruitmentDetailsScreen.RecruitmentDetailsScreen(
                    applicationId = it.arguments!!.getLong(Routes.Arguments.recruitmentApplicationId),
                    navController = navController,
                    showMessage = showMessage
                )
            }

            composable(Routes.recruitmentJobs) {
                recruitmentJobsScreen.RecruitmentJobsScreen(
                    navController = navController,
                    showMessage = showMessage
                )
            }

            composable(Routes.crm) {
                crmScreen.CrmScreen(
                    navController = navController,
                    showMessage = showMessage
                )
            }

            composable(Routes.userProfile) {
                userProfileScreen.UserProfileScreen(
                    navController = navController,
                    showMessage = showMessage
                )
            }

            composable(Routes.employees) {
                employeesScreen.EmployeesScreen(
                    navController = navController,
                    showMessage = showMessage
                )
            }

            composable(
                route = "${Routes.employeeDetails}/{${Routes.Arguments.employeesEmployeeId}}",
                arguments = listOf(
                    navArgument(Routes.Arguments.employeesEmployeeId) {
                        type = NavType.LongType
                    }
                )
            ) {
                employeeDetailsScreen.EmployeeDetailsScreen(
                    employeeId = it.arguments!!.getLong(Routes.Arguments.employeesEmployeeId),
                    navController = navController,
                    showMessage = showMessage
                )
            }
        }
    }
}
