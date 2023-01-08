package odoo.miem.android.feature.authorization.base.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

/**
 * [IAuthorizationScreen] - interface for wrapping [AuthorizationScreen], providing it for
 * external consumers and implementatiob
 *
 * @author Vorozhtsov Mikhail
 */
interface IAuthorizationScreen {

    @Composable
    fun AuthorizationScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit,
    )
}
