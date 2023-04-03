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
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
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
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.components.bottomsheet.change.RecruitmentLikeChangeStatusBottomSheetContent
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.components.bottomsheet.create.RecruitmentLikeCreateStatusBottomSheetContent
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.components.main.RecruitmentLikeScreenMainContent
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.components.screen.RecruitmentLikeSearchResult
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.RecruitmentBottomSheetState
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.RecruitmentLikeEmployeeModel
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.RecruitmentLikeStatusModel
import odoo.miem.android.common.uiKitComponents.utils.SharedElementConstants

@Suppress
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <S : RecruitmentLikeStatusModel<E>, E : RecruitmentLikeEmployeeModel> RecruitmentLikeBottomSheetLayout(
    scaffoldState: ModalBottomSheetState,
    statusList: List<S>,
    topRadius: Dp,
    scope: CoroutineScope,
    contentPaddingValues: PaddingValues,
    onStatusClicked: (E, S) -> Unit,
    onNewStatusCreated: (String, String) -> Unit,
    createStatusPictures: List<String>
) {
    var isSearchScreenVisible by remember { mutableStateOf(false) }
    var screenState by remember {
        mutableStateOf<RecruitmentBottomSheetState>(RecruitmentBottomSheetState.CreateStatus)
    }

    val onCreateStatusClick: () -> Unit = {
        screenState = RecruitmentBottomSheetState.CreateStatus
        scope.launch {
            scaffoldState.show()
        }
    }
    val onChangeStatusClick: (E) -> Unit = { employee ->
        screenState = RecruitmentBottomSheetState.ChangeStatus(employee)
        scope.launch {
            scaffoldState.show()
        }
    }

    val onNewStatusCreated: (String, String) -> Unit = { statusName, imageLink ->
        onNewStatusCreated(statusName, imageLink)
        scope.launch {
            scaffoldState.hide()
        }
    }

    ModalBottomSheetLayout(
        sheetState = scaffoldState,
        sheetContent = {
            /**
             * https://github.com/google/accompanist/issues/910, fixed in alpha for 0.29.0
             * TODO: Update the lib when it's stable
             **/
            Spacer(Modifier.height(1.dp))

            when (screenState) {
                is RecruitmentBottomSheetState.ChangeStatus<*> -> {
                    (screenState as? RecruitmentBottomSheetState.ChangeStatus<E>)?.employee?.let { employee ->
                        RecruitmentLikeChangeStatusBottomSheetContent(
                            statusList = statusList,
                            onCreateStatusClick = onCreateStatusClick,
                            onStatusClicked = onStatusClicked,
                            employee = employee
                        )
                    }
                }
                is RecruitmentBottomSheetState.CreateStatus -> RecruitmentLikeCreateStatusBottomSheetContent(
                    onCancelClick = {
                        scope.launch {
                            scaffoldState.hide()
                        }
                    },
                    onDoneClick = onNewStatusCreated,
                    pictures = createStatusPictures,
                )
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
                            scaffoldState.show()
                        }
                    },
                    onBackPressed = { isSearchScreenVisible = false },
                    onEmployeeClick = {
                        // TODO: Open Employee card
                    }
                )
            } else {
                RecruitmentLikeScreenMainContent(
                    statusList = statusList,
                    onEmployeeActionClick = onChangeStatusClick,
                    onSearchBarClicked = {
                        isSearchScreenVisible = true
                    },
                    onCreateStatusClick = onCreateStatusClick
                )
            }
        }
    }
}
