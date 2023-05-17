package odoo.miem.android.feature.employees.api

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController

/**
 * [IEmployeesScreen] - interface for wrapping [EmployeesScreen], provision
 * for external consumers and implementation
 *
 * @author Egor Danilov
 */
interface IEmployeesScreen {

    @Composable
    fun EmployeesScreen(
        viewModelStoreOwner: ViewModelStoreOwner,
        navController: NavHostController,
        showMessage: (Int) -> Unit
    )
}
