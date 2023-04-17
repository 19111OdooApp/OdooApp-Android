package odoo.miem.android.common.uiKitComponents.screen.recruitmentLike

import androidx.annotation.StringRes
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import com.mxalbert.sharedelements.SharedElementsRoot
import odoo.miem.android.common.uiKitComponents.bottomsheet.CustomBottomSheetValue
import odoo.miem.android.common.uiKitComponents.bottomsheet.rememberCustomBottomSheetScaffoldState
import odoo.miem.android.common.uiKitComponents.bottomsheet.rememberCustomBottomSheetState
import odoo.miem.android.common.uiKitComponents.buttons.SelectModulesFloatingActionButton
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.components.bottomsheet.RecruitmentLikeBottomSheetLayout
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.RecruitmentLikeEmployeeModel
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.RecruitmentLikeStatusModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <S : RecruitmentLikeStatusModel<E>, E : RecruitmentLikeEmployeeModel> RecruitmentLikeScreen(
    statusList: List<S>,
    onUserIconClick: () -> Unit = {},
    onNavigateToModulesPressed: () -> Unit,
    onStatusClick: (E, S) -> Unit,
    onNewStatusCreated: (String, String) -> Unit,
    createStatusPictures: List<String>,
    @StringRes searchHintRes: Int,
) =
    Scaffold(
        floatingActionButton = {
            SelectModulesFloatingActionButton(onNavigateToModulesPressed)
        },
        backgroundColor = MaterialTheme.colorScheme.background
    ) {
        SharedElementsRoot {
            val scope = rememberCoroutineScope()

            val topRadius = 35.dp
            val possibleStates = listOf(
                CustomBottomSheetValue.Hidden,
                CustomBottomSheetValue.Expanded,
            )
            val sheetState = rememberCustomBottomSheetState(
                initialValue = CustomBottomSheetValue.Hidden,
                possibleValues = possibleStates
            )
            val scaffoldState = rememberCustomBottomSheetScaffoldState(
                customBottomSheetState = sheetState,
                possibleValues = sheetState.possibleValues,
            )
            RecruitmentLikeBottomSheetLayout(
                scaffoldState = scaffoldState,
                statusList = statusList,
                topRadius = topRadius,
                scope = scope,
                contentPaddingValues = it,
                onUserIconClick = onUserIconClick,
                onStatusClicked = onStatusClick,
                onNewStatusCreated = onNewStatusCreated,
                createStatusPictures = createStatusPictures,
                searchHintRes = searchHintRes,
            )
        }
    }
