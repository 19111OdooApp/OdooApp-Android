package odoo.miem.android.feature.details.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

/**
 * [IDetailsScreen] - interface for wrapping [DetailsScreen], providing it for
 * external consumers and implementation
 *
 * @author Vorozhtsov Mikhail
 */
interface IDetailsScreen {

    @Composable
    fun DetailsScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit,
    )
}
