package odoo.miem.android.feature.employees.impl.employeesScreen.allEmployees.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import odoo.miem.android.common.network.employees.api.entities.EmployeeBasicInfo
import odoo.miem.android.core.uiKitTheme.commonPadding
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding

@Composable
internal fun ColumnScope.EmployeesList(
    employees: List<EmployeeBasicInfo>,
    onClick: (employee: EmployeeBasicInfo) -> Unit = {}
) = LazyColumn(
    modifier = Modifier
        .padding(horizontal = mainHorizontalPadding)
        .fillMaxSize()
) {
    items(employees) {
        EmployeeCard(
            employee = it,
            onClick = onClick
        )

        Spacer(modifier = Modifier.height(commonPadding))
    }
}