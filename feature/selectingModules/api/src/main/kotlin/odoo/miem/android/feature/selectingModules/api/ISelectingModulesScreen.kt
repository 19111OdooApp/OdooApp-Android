package odoo.miem.android.feature.selectingModules.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

/**
 * [ISelectingModulesScreen] - interface for wrapping [SelectingModulesScreen], provision
 * for external consumers and implementation
 *
 * @author Vorozhtsov Mikhail
 */
interface ISelectingModulesScreen {

    @Composable
    fun SelectingModulesScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit
    )
}
