package odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.components.bottomsheet

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import odoo.miem.android.common.uiKitComponents.bottomsheet.CustomBottomSheetScaffold
import odoo.miem.android.common.uiKitComponents.bottomsheet.CustomBottomSheetScaffoldState
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.components.bottomsheet.change.RecruitmentLikeChangeStatusBottomSheetContent
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.components.bottomsheet.create.RecruitmentLikeCreateStatusBottomSheetContent
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.components.main.RecruitmentLikeScreenMainContent
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.components.screen.RecruitmentLikeSearchResult
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.RecruitmentBottomSheetState
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.RecruitmentLikeEmployeeModel
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.RecruitmentLikeStatusModel
import odoo.miem.android.common.uiKitComponents.utils.SharedElementConstants
import odoo.miem.android.common.uiKitComponents.utils.getStatusIcon

@Suppress
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <S : RecruitmentLikeStatusModel<E>, E : RecruitmentLikeEmployeeModel> RecruitmentLikeBottomSheetLayout(
    avatarUrl: String?,
    userName: String,
    scaffoldState: CustomBottomSheetScaffoldState,
    statusList: List<S>,
    topRadius: Dp,
    scope: CoroutineScope,
    contentPaddingValues: PaddingValues,
    onUserIconClick: () -> Unit = {},
    onStatusClicked: (E, S) -> Unit,
    onEmployeeCardClick: (E) -> Unit,
    onNewStatusCreated: (statusName: String) -> Unit,
    searchHintRes: Int,
) {
    var isSearchScreenVisible by remember { mutableStateOf(false) }
    var screenState by remember {
        mutableStateOf<RecruitmentBottomSheetState>(RecruitmentBottomSheetState.Empty)
    }
    if (scaffoldState.customBottomSheetState.isHidden) {
        screenState = RecruitmentBottomSheetState.Empty
    }

    val onCreateStatusClick: () -> Unit = {
        screenState = RecruitmentBottomSheetState.CreateStatus
        scope.launch {
            scaffoldState.customBottomSheetState.expand()
        }
    }
    val onChangeStatusClick: (E) -> Unit = { employee ->
        screenState = RecruitmentBottomSheetState.ChangeStatus(employee)
        scope.launch {
            scaffoldState.customBottomSheetState.expand()
        }
    }

    val onNewStatusCreated: (String) -> Unit = { statusName ->
        onNewStatusCreated(statusName)
        scope.launch {
            scaffoldState.customBottomSheetState.hide()
        }
    }

    CustomBottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            when (screenState) {
                is RecruitmentBottomSheetState.ChangeStatus<*> -> {
                    (screenState as? RecruitmentBottomSheetState.ChangeStatus<E>)?.employee?.let { employee ->
                        RecruitmentLikeChangeStatusBottomSheetContent(
                            statusList = statusList,
                            onCreateStatusClick = onCreateStatusClick,
                            onStatusClicked = onStatusClicked,
                            employee = employee,
                        )
                    }
                }

                is RecruitmentBottomSheetState.CreateStatus -> RecruitmentLikeCreateStatusBottomSheetContent(
                    onCancelClick = {
                        scope.launch {
                            scaffoldState.customBottomSheetState.hide()
                        }
                    },
                    onDoneClick = onNewStatusCreated,
                    iconRes = getStatusIcon(statusList.size)
                )

                is RecruitmentBottomSheetState.Empty -> {
                    // Dirty hack so that animations would work normally
                    Spacer(Modifier.height(1.dp))
                }
            }
        },
        sheetShape = RoundedCornerShape(
            topStart = topRadius,
            topEnd = topRadius
        ),
        sheetElevation = 8.dp,
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        possibleValues = scaffoldState.customBottomSheetState.possibleValues,
    ) {
        Crossfade(
            targetState = isSearchScreenVisible,
            animationSpec = tween(durationMillis = SharedElementConstants.transitionDurationMills),
            modifier = Modifier.padding(contentPaddingValues)
        ) { visible ->
            if (visible) {
                RecruitmentLikeSearchResult(
                    employees = statusList.flatMap { it.employees }
                        .sortedBy { it.name },
                    onEmployeeActionClick = {
                        scope.launch {
                            scaffoldState.customBottomSheetState.expand()
                        }
                    },
                    onBackPressed = { isSearchScreenVisible = false },
                    onEmployeeClick = { onEmployeeCardClick(it) },
                )
            } else {
                RecruitmentLikeScreenMainContent(
                    avatarUrl = avatarUrl,
                    userName = userName,
                    statusList = statusList,
                    onEmployeeActionClick = onChangeStatusClick,
                    onSearchBarClicked = {
                        isSearchScreenVisible = true
                    },
                    onEmployeeCardClick = onEmployeeCardClick,
                    onCreateStatusClick = onCreateStatusClick,
                    searchHintRes = searchHintRes,
                    onUserIconClick = onUserIconClick,
                )
            }
        }
    }
}
