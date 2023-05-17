package odoo.miem.android.feature.employees.impl.employeesScreen.employeeDetails

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import odoo.miem.android.feature.employees.api.IEmployeeDetailsScreen
import javax.inject.Inject

class EmployeeDetailsScreen @Inject constructor() : IEmployeeDetailsScreen {

    @SuppressLint("NotConstructor")
    @Composable
    override fun EmployeeDetailsScreen(
        employeeId: Long,
        viewModelStoreOwner: ViewModelStoreOwner,
        navController: NavHostController,
        showMessage: (Int) -> Unit
    ) {
    }
}
