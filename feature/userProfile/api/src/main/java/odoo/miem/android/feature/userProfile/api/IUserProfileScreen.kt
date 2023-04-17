package odoo.miem.android.feature.userProfile.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

/**
 * [IUserProfileScreen] - interface for wrapping [UserProfileScreen], provision
 * for external consumers and implementation
 *
 * @author Egor Danilov
 */
interface IUserProfileScreen {

    @Composable
    fun UserProfileScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit
    )
}
