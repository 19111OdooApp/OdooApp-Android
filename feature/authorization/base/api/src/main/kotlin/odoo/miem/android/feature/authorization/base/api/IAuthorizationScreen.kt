package odoo.miem.android.feature.authorization.base.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

/**
 * [IAuthorizationScreen] - интерфейс для обертки [AuthorizationScreen], предоставления
 * для внешних потребителей и реализации
 *
 * @author Ворожцов Михаил
 */
interface IAuthorizationScreen {

    @Composable
    fun AuthorizationScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit
    )
}
