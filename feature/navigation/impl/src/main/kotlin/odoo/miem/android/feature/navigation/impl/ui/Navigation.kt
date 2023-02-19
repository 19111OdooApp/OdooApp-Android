
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
import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.feature.authorization.base.api.IAuthorizationScreen
import odoo.miem.android.feature.authorization.base.api.di.IAuthorizationApi
import odoo.miem.android.feature.moduleNotFound.api.IModuleNotFoundScreen
import odoo.miem.android.feature.moduleNotFound.api.di.IModuleNotFoundApi
import odoo.miem.android.feature.navigation.api.data.Routes
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
    val dataStore by api(IDataStoreApi::dataStore)

    // Screens
    val authorizationScreen by api(IAuthorizationApi::authorizationScreen)
    val selectingModulesScreen by api(ISelectingModulesApi::selectingModulesScreen)
    val moduleNotFoundScreen by api(IModuleNotFoundApi::moduleNotFoundScreen)

    NavigationContent(
        authorizationScreen = authorizationScreen,
        selectingModulesScreen = selectingModulesScreen,
        moduleNotFoundScreen = moduleNotFoundScreen,
        paddingValues = paddingValues,
        navController = navController,
//        isAuthorized = dataStore.isAuthorized,
        showMessage = showMessage,
    )
}

@Composable
fun NavigationContent(
    authorizationScreen: IAuthorizationScreen,
    selectingModulesScreen: ISelectingModulesScreen,
    moduleNotFoundScreen: IModuleNotFoundScreen,
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
        }
    }
}
