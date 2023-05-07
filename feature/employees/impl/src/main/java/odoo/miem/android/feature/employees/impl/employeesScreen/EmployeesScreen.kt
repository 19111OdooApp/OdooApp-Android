package odoo.miem.android.feature.employees.impl.employeesScreen

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.core.utils.rx.collectAsState
import odoo.miem.android.core.utils.state.SuccessResult
import odoo.miem.android.core.utils.state.subscribeOnError
import odoo.miem.android.feature.employees.api.IEmployeesScreen
import odoo.miem.android.feature.employees.impl.EmployeesScreenViewModel
import javax.inject.Inject

/**
 * [EmployeesScreen] implementation of [IEmployeesScreen]
 *
 * Methods by purpose:
 * - [EmployeesScreen] - the input point to this screen is needed for initial initialization.
 * For example, ViewModel initialization
 * - [EmployeesScreenContent] - directly layout of this screen
 * - [EmployeesScreenPreview] - preview of the layout that turned out in [EmployeesScreenContent]
 *
 * @author Egor Danilov
 */
class EmployeesScreen @Inject constructor() : IEmployeesScreen {

    @SuppressLint("NotConstructor")
    @Composable
    override fun EmployeesScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit
    ) {
        val viewModel: EmployeesScreenViewModel = viewModel()

        val allEmployeesState by viewModel.allEmployeesState.collectAsState()
        allEmployeesState.subscribeOnError(showMessage)

        val employeeDetailsState by viewModel.employeeDetails.collectAsState()
        employeeDetailsState.subscribeOnError(showMessage)

        if (allEmployeesState is SuccessResult) {
            LaunchedEffect(Unit) {
                allEmployeesState.data
                    ?.get(0)
                    ?.id
                    ?.let { viewModel.getEmployeeDetails(it) }
            }
        }

        LaunchedEffect(Unit) {
            viewModel.getAllEmployees()
        }

        EmployeesScreenContent()
    }

    @Composable
    private fun EmployeesScreenContent() {
    }

    @Composable
    @Preview(showBackground = true, backgroundColor = 0xFFFFFF)
    private fun EmployeesScreenPreview() = OdooMiemAndroidTheme {
        EmployeesScreenContent()
    }
}
