package odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.components.main

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import odoo.miem.android.common.uiKitComponents.R
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.components.header.RecruitmentLikeScreenHeader
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.components.screen.RecruitmentLikeScreenProgressBar
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.components.screen.RecruitmentLikeScreenSearch
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.RecruitmentLikeEmployeeModel
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.RecruitmentLikeStatusModel
import odoo.miem.android.core.uiKitTheme.halfMainVerticalPadding
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding

@OptIn(ExperimentalPagerApi::class)
@Composable
fun <S : RecruitmentLikeStatusModel<E>, E : RecruitmentLikeEmployeeModel> RecruitmentLikeScreenMainContent(
    statusList: List<S>,
    onEmployeeActionClick: (E) -> Unit,
    onSearchBarClicked: () -> Unit,
    onCreateStatusClick: () -> Unit,
    @StringRes searchHintRes: Int,
) = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceBetween,
) {
    val pagerState = rememberPagerState()

    val headerTitle = if (statusList.size == pagerState.currentPage) {
        stringResource(id = R.string.recruitment_add_new_status)
    } else {
        statusList[pagerState.currentPage].statusName
    }

    RecruitmentLikeScreenHeader(title = headerTitle)

    RecruitmentLikeScreenSearch(
        onSearchBarClicked = onSearchBarClicked,
        searchHintRes = searchHintRes,
    )

    Spacer(modifier = Modifier.padding(top = 16.dp))

    HorizontalPagerIndicator(
        pagerState = pagerState,
        modifier = Modifier
            .padding(horizontal = mainHorizontalPadding),
        activeColor = MaterialTheme.colorScheme.onTertiary,
        inactiveColor = MaterialTheme.colorScheme.tertiary,
    )

    if (statusList.size != pagerState.currentPage) {
        Spacer(modifier = Modifier.padding(top = halfMainVerticalPadding))

        RecruitmentLikeScreenProgressBar(
            progress = 0.0f,
            count = statusList[pagerState.currentPage].employees.size
        )
    }

    RecruitmentLikePager(
        statusList = statusList,
        pagerState = pagerState,
        onEmployeeActionClick = onEmployeeActionClick,
        onCreateStatusClick = onCreateStatusClick,
    )
}
