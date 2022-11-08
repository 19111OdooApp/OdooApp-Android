package odoo.miem.android.feature.navigation.ui

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
import odoo.miem.android.feature.navigation.data.Routes

/**
 * [Navigation] composable функция, которая предназначена для
 * инициализации навигации между экранами
 *
 * Для добавления нового экрана пропишите соответсвующий путь до него
 * в [Routes] и добавьте новую [composable] в [NavHost]
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

    NavigationContent(
        authorizationScreen = authorizationScreen,
        paddingValues = paddingValues,
        navController = navController,
        showMessage = showMessage
    )
}

@Composable
fun NavigationContent(
    authorizationScreen: IAuthorizationScreen,
    paddingValues: PaddingValues,
    navController: NavHostController,
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
                Routes.authorization // TODO Depends on is login in or not
            }
        ) {
            composable(Routes.authorization) {
                authorizationScreen.AuthorizationScreen(
                    navController = navController,
                    showMessage = showMessage
                )
            }
        }
    }
}