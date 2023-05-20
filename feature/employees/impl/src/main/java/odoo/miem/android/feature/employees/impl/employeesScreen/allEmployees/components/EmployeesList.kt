package odoo.miem.android.feature.employees.impl.employeesScreen.allEmployees.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import odoo.miem.android.common.network.employees.api.entities.EmployeeAvatarRequestHeaders
import odoo.miem.android.common.network.employees.api.entities.EmployeeBasicInfo
import odoo.miem.android.core.uiKitTheme.commonPadding
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding

@Suppress("MagicNumber")
@Composable
internal fun ColumnScope.EmployeesList(
    employees: List<EmployeeBasicInfo>,
    employeeAvatarRequestHeaders: List<EmployeeAvatarRequestHeaders>,
    onClick: (employee: EmployeeBasicInfo) -> Unit = {},
    onReachBottom: () -> Unit = {},
) = Box {
    val lazyColumnState = rememberLazyListState()

    val isBottomReached by remember {
        derivedStateOf {
            lazyColumnState.firstVisibleItemIndex == employees.size - 5
        }
    }

    if (isBottomReached) {
        onReachBottom()
    }

    LazyColumn(
        state = lazyColumnState,
        modifier = Modifier
            .padding(horizontal = mainHorizontalPadding)
            .fillMaxSize()
    ) {
        items(
            items = employees,
            key = { employee -> employee.id }
        ) {
            EmployeeCard(
                employee = it,
                employeeAvatarRequestHeaders = employeeAvatarRequestHeaders,
                onClick = onClick
            )

            Spacer(modifier = Modifier.height(commonPadding))
        }
    }
}
