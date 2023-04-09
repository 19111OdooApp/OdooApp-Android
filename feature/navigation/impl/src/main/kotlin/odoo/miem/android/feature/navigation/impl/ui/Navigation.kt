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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.launch
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.feature.authorization.base.api.IAuthorizationScreen
import odoo.miem.android.feature.authorization.base.api.di.IAuthorizationApi
import odoo.miem.android.feature.details.api.IDetailsScreen
import odoo.miem.android.feature.details.api.di.IDetailsScreenApi
import odoo.miem.android.feature.moduleNotFound.api.IModuleNotFoundScreen
import odoo.miem.android.feature.moduleNotFound.api.di.IModuleNotFoundApi
import odoo.miem.android.feature.navigation.api.data.Routes
import odoo.miem.android.feature.recruitment.api.IRecruitmentScreen
import odoo.miem.android.feature.recruitment.api.di.IRecruitmentApi
import odoo.miem.android.feature.selectingModules.api.ISelectingModulesScreen
import odoo.miem.android.feature.selectingModules.api.di.ISelectingModulesApi

/**
 * [Navigation] composable function, which is needed to initialize navigation across screens
 *
 * For adding new screen one should write corresponding path to [Routes]
 * and add new [composable] в [NavHost]
 *
 * @author Ворожцов Михаил
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
    val detailsScreen by api(IDetailsScreenApi::detailsScreen)
    val selectingModulesScreen by api(ISelectingModulesApi::selectingModulesScreen)
    val moduleNotFoundScreen by api(IModuleNotFoundApi::moduleNotFoundScreen)
    val recruitmentScreen by api(IRecruitmentApi::recruitmentScreen)
    NavigationContent(
        authorizationScreen = authorizationScreen,
        detailsScreen = detailsScreen,
        selectingModulesScreen = selectingModulesScreen,
        moduleNotFoundScreen = moduleNotFoundScreen,
        recruitmentScreen = recruitmentScreen,
        paddingValues = paddingValues,
        navController = navController,
//        isAuthorized = dataStore.isAuthorized,
        showMessage = showMessage,
    )
}

@Composable
fun NavigationContent(
    authorizationScreen: IAuthorizationScreen,
    detailsScreen: IDetailsScreen,
    selectingModulesScreen: ISelectingModulesScreen,
    moduleNotFoundScreen: IModuleNotFoundScreen,
    recruitmentScreen: IRecruitmentScreen,
    paddingValues: PaddingValues,
    navController: NavHostController,
//    isAuthorized: Boolean,
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
                Routes.authorization
            }
        ) {
            composable(Routes.authorization) {
                authorizationScreen.AuthorizationScreen(
                    navController = navController,
                    showMessage = showMessage
                )
            }

            composable(Routes.details) {
                detailsScreen.DetailsScreen(
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

            composable(Routes.recruitment) {
                recruitmentScreen.RecruitmentScreen(
                    navController = navController,
                    showMessage = showMessage
                )
            }
        }
    }
}
