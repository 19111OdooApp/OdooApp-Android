package odoo.miem.android.feature.recruitment.impl.screen.jobs

import android.annotation.SuppressLint
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import odoo.miem.android.common.uiKitComponents.R
import odoo.miem.android.common.uiKitComponents.bottomsheet.CustomBottomSheetValue
import odoo.miem.android.common.uiKitComponents.bottomsheet.rememberCustomBottomSheetScaffoldState
import odoo.miem.android.common.uiKitComponents.bottomsheet.rememberCustomBottomSheetState
import odoo.miem.android.common.uiKitComponents.screen.searchLike.SearchLikeScreen
import odoo.miem.android.feature.recruitment.api.IRecruitmentJobsScreen
import javax.inject.Inject

class RecruitmentJobsScreen @Inject constructor() : IRecruitmentJobsScreen {

    @SuppressLint("NotConstructor")
    @Composable
    override fun RecruitmentJobsScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit
    ) {
        RecruitmentJobsScreenContent()
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun RecruitmentJobsScreenContent() {
        val sheetState = rememberCustomBottomSheetState(
            initialValue = CustomBottomSheetValue.Hidden,
            possibleValues = listOf(
                CustomBottomSheetValue.Hidden,
                CustomBottomSheetValue.Expanded,
            )
        )
        val scaffoldState = rememberCustomBottomSheetScaffoldState(
            customBottomSheetState = sheetState,
            possibleValues = sheetState.possibleValues,
        )

        SearchLikeScreen(
            items = emptyList(),
            scaffoldState = scaffoldState,
            sheetContent = {}, // TODO
            userName = stringResource(id = R.string.default_user_name), // TODO?
            mainTitle = "Some cool title" // TODO
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun RecruitmentJobsScreenPreview() {
        RecruitmentJobsScreenContent()
    }
}
