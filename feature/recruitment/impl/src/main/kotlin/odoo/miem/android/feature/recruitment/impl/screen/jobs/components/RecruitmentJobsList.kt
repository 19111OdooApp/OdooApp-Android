package odoo.miem.android.feature.recruitment.impl.screen.jobs.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import odoo.miem.android.common.uiKitComponents.cards.BigJobCard
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.mainVerticalPadding
import odoo.miem.android.feature.recruitment.impl.screen.jobs.model.RecruitmentJob

@Composable
internal fun ColumnScope.RecruitmentJobsList(
    jobs: List<RecruitmentJob>,
    onLongClick: () -> Unit = {}
) = LazyColumn(
    modifier = Modifier
        .padding(horizontal = mainHorizontalPadding)
        .fillMaxSize()
) {
    items(jobs) {
        // TODO Add data
        BigJobCard(
            jobName = it.name,
            onLongClick = onLongClick,
            isPublished = true
        )

        Spacer(modifier = Modifier.height(mainVerticalPadding / 2))
    }
}
