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
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.DeadlineStatus
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.RecruitmentLikeEmployeeModel
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.RecruitmentLikeStatusModel
import odoo.miem.android.core.uiKitTheme.halfMainVerticalPadding
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding

@OptIn(ExperimentalPagerApi::class)
@Composable
fun <S : RecruitmentLikeStatusModel<E>, E : RecruitmentLikeEmployeeModel> RecruitmentLikeScreenMainContent(
    avatarUrl: String?,
    userName: String,
    statusList: List<S>,
    onUserIconClick: () -> Unit = {},
    onEmployeeActionClick: (E) -> Unit,
    onEmployeeCardClick: (E) -> Unit,
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

    RecruitmentLikeScreenHeader(
        avatarUrl = avatarUrl,
        userName = userName,
        title = headerTitle,
        onUserIconClick = onUserIconClick
    )

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

    if (statusList.size != pagerState.currentPage && statusList[pagerState.currentPage].employees.isNotEmpty()) {
        Spacer(modifier = Modifier.padding(top = halfMainVerticalPadding))
        val statusDivisionMap = mutableMapOf<DeadlineStatus, Int>()

        // Do it for consistency
        DeadlineStatus.values().forEach { statusDivisionMap[it] = 0 }
        statusList[pagerState.currentPage].employees.forEach {
            statusDivisionMap[it.deadlineStatus] = 1 + (statusDivisionMap[it.deadlineStatus] ?: 0)
        }

        RecruitmentLikeScreenProgressBar(
            statusDivisionMap = statusDivisionMap,
            count = statusList[pagerState.currentPage].employees.size
        )
    }

    RecruitmentLikePager(
        statusList = statusList,
        pagerState = pagerState,
        onEmployeeActionClick = onEmployeeActionClick,
        onEmployeeCardClick = onEmployeeCardClick,
        onCreateStatusClick = onCreateStatusClick,
    )
}
