package odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.components.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import odoo.miem.android.common.uiKitComponents.buttons.GradientRoundButton
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.RecruitmentLikeEmployeeModel
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.RecruitmentLikeStatusModel
import odoo.miem.android.core.uiKitTheme.gradientColors
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding

@OptIn(ExperimentalPagerApi::class)
@Composable
fun <E : RecruitmentLikeEmployeeModel, S : RecruitmentLikeStatusModel<E>> RecruitmentLikePager(
    statusList: List<S>,
    pagerState: PagerState,
    onEmployeeActionClick: (E) -> Unit,
    onEmployeeCardClick: (E) -> Unit,
    onCreateStatusClick: () -> Unit,
) = HorizontalPager(
    count = statusList.size + 1,
    state = pagerState,
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.Top,
) { page ->

    if (page < statusList.size) {
        RecruitmentLikeList(
            employees = statusList[page].employees,
            onEmployeeCardClick = onEmployeeCardClick,
            onEmployeeActionClick = onEmployeeActionClick,
            modifier = Modifier
        )
    } else {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = mainHorizontalPadding)
        ) {
            GradientRoundButton(
                modifier = Modifier
                    .size(100.dp),
                colors = gradientColors,
                onClick = onCreateStatusClick
            )
        }
    }
}
