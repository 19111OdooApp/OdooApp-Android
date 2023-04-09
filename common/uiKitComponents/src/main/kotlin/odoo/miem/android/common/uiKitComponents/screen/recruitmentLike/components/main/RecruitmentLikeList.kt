package odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.components.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import odoo.miem.android.common.uiKitComponents.cards.OutlinedEmployeeCard
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.DeadlineStatus
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.RecruitmentLikeEmployeeModel
import odoo.miem.android.core.uiKitTheme.commonPadding
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.mainVerticalPadding
import odoo.miem.android.core.uiKitTheme.odooErrorPrimaryDark
import odoo.miem.android.core.uiKitTheme.odooPrimaryDark
import odoo.miem.android.core.uiKitTheme.odooPrimaryGray

@Composable
fun <E : RecruitmentLikeEmployeeModel> RecruitmentLikeList(
    employees: List<E>,
    onEmployeeCardClick: (E) -> Unit,
    onEmployeeActionClick: (E) -> Unit,
    modifier: Modifier = Modifier,
) = LazyColumn(
    verticalArrangement = Arrangement.spacedBy(mainVerticalPadding),
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = mainHorizontalPadding)
        .padding(top = commonPadding)
) {
    items(employees) {
        OutlinedEmployeeCard(
            rating = it.rating,
            employeeName = it.name,
            onClick = { onEmployeeCardClick(it) },
            onActionClick = { onEmployeeActionClick(it) },
            scheduleIconColor = getClockColor(it.deadlineStatus),
            actionIconPainter = it.imageUrl,
        )
    }
}

private fun getClockColor(deadlineStatus: DeadlineStatus): Color {
    return when (deadlineStatus) {
        DeadlineStatus.NO_TASKS -> odooPrimaryGray
        DeadlineStatus.EXPIRED -> odooErrorPrimaryDark
        DeadlineStatus.ACTIVE -> odooPrimaryDark
    }
}