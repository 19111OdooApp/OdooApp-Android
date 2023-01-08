package odoo.miem.android.feature.moduleNotFound.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

/**
 * [IModuleNotFoundScreen] - interface for wrapping [ModuleNotFoundScreen], provision
 * for external consumers and implementation
 *
 * @author Egor Danilov
 */
interface IModuleNotFoundScreen {

    @Composable
    fun ModuleNotFoundScreen(
        navController: NavHostController,
    )
}