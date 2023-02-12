package odoo.miem.android.feature.profile.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

/**
 * [IProfileScreen] - interface for wrapping [ProfileScreen], providing it for
 * external consumers and implementation
 *
 * @author Vorozhtsov Mikhail
 */
interface IProfileScreen {

    @Composable
    fun ProfileScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit,
    )
}