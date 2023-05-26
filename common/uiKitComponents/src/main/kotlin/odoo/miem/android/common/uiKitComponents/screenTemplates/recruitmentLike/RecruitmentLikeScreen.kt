package odoo.miem.android.common.uiKitComponents.screenTemplates.recruitmentLike

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.mxalbert.sharedelements.SharedElementsRoot
import kotlinx.coroutines.launch
import odoo.miem.android.common.uiKitComponents.bottomsheet.CustomBottomSheetScaffoldState
import odoo.miem.android.common.uiKitComponents.bottomsheet.CustomBottomSheetValue
import odoo.miem.android.common.uiKitComponents.bottomsheet.rememberCustomBottomSheetScaffoldState
import odoo.miem.android.common.uiKitComponents.bottomsheet.rememberCustomBottomSheetState
import odoo.miem.android.common.uiKitComponents.buttons.SelectModulesFloatingActionButton
import odoo.miem.android.common.uiKitComponents.screenTemplates.recruitmentLike.components.bottomsheet.RecruitmentLikeBottomSheetLayout
import odoo.miem.android.common.uiKitComponents.screenTemplates.recruitmentLike.model.RecruitmentLikeEmployeeModel
import odoo.miem.android.common.uiKitComponents.screenTemplates.recruitmentLike.model.RecruitmentLikeStatusModel

@Composable
fun <S : RecruitmentLikeStatusModel<E>, E : RecruitmentLikeEmployeeModel> RecruitmentLikeScreen(
    avatarUrl: String? = null,
    userName: String,
    statusList: List<S>,
    onUserIconClick: () -> Unit = {},
    onNavigateToModulesPressed: () -> Unit,
    onStatusClick: (E, S) -> Unit,
    onNewStatusCreated: (statusName: String) -> Unit,
    onEmployeeCardClick: (E) -> Unit = {},
    onBackPressed: () -> Unit = {},
    scaffoldState: CustomBottomSheetScaffoldState = defaultScaffoldState(),
    @StringRes searchHintRes: Int,
) = Scaffold(
    floatingActionButton = {
        var isVisible by remember {
            mutableStateOf(scaffoldState.customBottomSheetState.isHidden)
        }

        LaunchedEffect(scaffoldState.customBottomSheetState.currentValue) {
            isVisible = scaffoldState.customBottomSheetState.isHidden
        }

        AnimatedVisibility(isVisible) {
            SelectModulesFloatingActionButton(
                onNavigateToModulesPressed
            )
        }
    },
    backgroundColor = MaterialTheme.colorScheme.background
) {
    SharedElementsRoot {
        val scope = rememberCoroutineScope()

        val topRadius = 35.dp

        BackHandler(enabled = true) {
            if (scaffoldState.customBottomSheetState.isExpanded) {
                scope.launch {
                    scaffoldState.customBottomSheetState.hide()
                }
            } else {
                onBackPressed()
            }
        }

        RecruitmentLikeBottomSheetLayout(
            avatarUrl = avatarUrl,
            userName = userName,
            scaffoldState = scaffoldState,
            statusList = statusList,
            topRadius = topRadius,
            scope = scope,
            contentPaddingValues = it,
            onUserIconClick = onUserIconClick,
            onStatusClicked = onStatusClick,
            onNewStatusCreated = onNewStatusCreated,
            onEmployeeCardClick = onEmployeeCardClick,
            searchHintRes = searchHintRes,
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun defaultScaffoldState(): CustomBottomSheetScaffoldState {
    val possibleStates = listOf(
        CustomBottomSheetValue.Hidden,
        CustomBottomSheetValue.Expanded,
    )
    val sheetState = rememberCustomBottomSheetState(
        initialValue = CustomBottomSheetValue.Hidden,
        possibleValues = possibleStates
    )

    return rememberCustomBottomSheetScaffoldState(
        customBottomSheetState = sheetState,
        possibleValues = sheetState.possibleValues,
    )
}
