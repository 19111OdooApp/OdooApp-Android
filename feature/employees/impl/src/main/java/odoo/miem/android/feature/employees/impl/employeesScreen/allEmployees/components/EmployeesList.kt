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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import odoo.miem.android.common.network.employees.api.entities.EmployeeBasicInfo
import odoo.miem.android.common.uiKitComponents.screen.base.searchLike.model.ScreenPage
import odoo.miem.android.common.uiKitComponents.switcher.LazyColumnPageSwitcher
import odoo.miem.android.common.uiKitComponents.text.LabelText
import odoo.miem.android.common.utils.avatar.AvatarRequestHeader
import odoo.miem.android.core.uiKitTheme.commonPadding
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.feature.employees.impl.R

@Suppress("MagicNumber")
@Composable
internal fun ColumnScope.EmployeesList(
    employeesPage: ScreenPage<EmployeeBasicInfo>,
    avatarRequestHeaders: List<AvatarRequestHeader>,
    onEmployeeClick: (employee: EmployeeBasicInfo) -> Unit = {},
    onChangePageClick: (newPage: Int) -> Unit = {},
) = Box {
    val lazyColumnState = rememberLazyListState()

    val maximumSize = employeesPage.maxSize
    val currentPage = employeesPage.currentPage
    val pageSize = employeesPage.pageSize

    val fromIndex = employeesPage.fromIndex
    val toIndex = employeesPage.toIndex

    val pageSwitcher: @Composable () -> Unit = if (
        maximumSize != null &&
        currentPage != null &&
        pageSize != null &&
        fromIndex != null &&
        toIndex != null
    ) {
        {
            val isBackButtonEnabled = currentPage != 0
            val isNextButtonEnabled = (currentPage + 1) * pageSize < maximumSize

            LazyColumnPageSwitcher(
                previousPageButtonEnabled = isBackButtonEnabled,
                nextPageButtonEnabled = isNextButtonEnabled,
                onPreviousPageClick = {
                    onChangePageClick(currentPage - 1)
                },
                onNextPageClick = {
                    onChangePageClick(currentPage + 1)
                }
            ) {
                LabelText(
                    text = stringResource(R.string.page_switcher_title)
                        .format(
                            "${fromIndex + 1}-$toIndex",
                            maximumSize
                        ),
                    isMedium = false
                )
            }
        }
    } else { {} }

    LazyColumn(
        state = lazyColumnState,
        modifier = Modifier
            .padding(horizontal = mainHorizontalPadding)
            .fillMaxSize()
    ) {
        item {
            pageSwitcher()
        }

        items(items = employeesPage.items) {
            EmployeeCard(
                employee = it,
                avatarRequestHeaders = avatarRequestHeaders,
                onClick = onEmployeeClick
            )

            Spacer(modifier = Modifier.height(commonPadding))
        }

        item {
            pageSwitcher()
        }
    }
}
