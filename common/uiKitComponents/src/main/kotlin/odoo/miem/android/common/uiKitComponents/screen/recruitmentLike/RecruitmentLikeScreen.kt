package odoo.miem.android.common.uiKitComponents.screen.recruitmentLike

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import com.mxalbert.sharedelements.SharedElementsRoot
import odoo.miem.android.common.uiKitComponents.buttons.SelectModulesFloatingActionButton
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.components.bottomsheet.RecruitmentLikeBottomSheetLayout
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.RecruitmentLikeEmployeeModel
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.RecruitmentLikeStatusModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <S : RecruitmentLikeStatusModel<E>, E : RecruitmentLikeEmployeeModel> RecruitmentLikeScreen(
    statusList: List<S>,
    onNavigateToModulesPressed: () -> Unit,
    onStatusClick: (E, S) -> Unit,
    onNewStatusCreated: (String, String) -> Unit,
    createStatusPictures: List<String>
) =
    Scaffold(
        floatingActionButton = {
            SelectModulesFloatingActionButton(onNavigateToModulesPressed)
        },
        backgroundColor = MaterialTheme.colorScheme.background
    ) {
        SharedElementsRoot {
            val scope = rememberCoroutineScope()

            val scaffoldState =
                rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

            val topRadius = 35.dp

            RecruitmentLikeBottomSheetLayout(
                scaffoldState = scaffoldState,
                statusList = statusList,
                topRadius = topRadius,
                scope = scope,
                contentPaddingValues = it,
                onStatusClicked = onStatusClick,
                onNewStatusCreated = onNewStatusCreated,
                createStatusPictures = createStatusPictures
            )
        }
    }
