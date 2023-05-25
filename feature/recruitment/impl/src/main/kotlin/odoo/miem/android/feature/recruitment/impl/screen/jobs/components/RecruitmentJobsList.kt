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
import odoo.miem.android.common.network.recruitment.api.entities.jobs.RecruitmentJob
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.mainVerticalPadding

@Composable
internal fun ColumnScope.RecruitmentJobsList(
    jobs: List<RecruitmentJob>,
    onLikeClick: (job: RecruitmentJob) -> Unit = {},
    onClick: (job: RecruitmentJob) -> Unit = {},
    onLongClick: (job: RecruitmentJob) -> Unit = {}
) = LazyColumn(
    modifier = Modifier
        .padding(horizontal = mainHorizontalPadding)
        .fillMaxSize()
) {
    items(jobs) {
        BigJobCard(
            job = it,
            onLikeClick = onLikeClick,
            onClick = onClick,
            onLongClick = onLongClick,
        )

        Spacer(modifier = Modifier.height(mainVerticalPadding / 2))
    }
}
