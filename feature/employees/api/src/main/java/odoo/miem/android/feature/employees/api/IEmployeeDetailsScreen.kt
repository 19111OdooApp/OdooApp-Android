package odoo.miem.android.feature.employees.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

/**
 * [IEmployeeDetailsScreen] - interface for wrapping [EmployeeDetailsScreen], provision
 * for external consumers and implementation
 *
 * @author Egor Danilov
 */
interface IEmployeeDetailsScreen {

    @Composable
    fun EmployeeDetailsScreen(
        employeeId: Long,
        navController: NavHostController,
        showMessage: (Int) -> Unit
    )
}
