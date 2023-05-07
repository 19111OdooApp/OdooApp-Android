package odoo.miem.android.feature.recruitment.impl.screen.jobs

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import odoo.miem.android.common.uiKitComponents.bottomsheet.CustomBottomSheetValue
import odoo.miem.android.common.uiKitComponents.bottomsheet.rememberCustomBottomSheetScaffoldState
import odoo.miem.android.common.uiKitComponents.bottomsheet.rememberCustomBottomSheetState
import odoo.miem.android.common.uiKitComponents.screen.searchLike.SearchLikeScreen
import odoo.miem.android.feature.recruitment.api.IRecruitmentJobsScreen
import odoo.miem.android.feature.recruitment.impl.R
import odoo.miem.android.feature.recruitment.impl.screen.jobs.components.RecruitmentJobsList
import odoo.miem.android.feature.recruitment.impl.screen.jobs.components.RecruitmentJobsSheet
import odoo.miem.android.feature.recruitment.impl.screen.jobs.model.RecruitmentJob
import timber.log.Timber
import javax.inject.Inject
import odoo.miem.android.common.uiKitComponents.R as uiKitComponentsR

class RecruitmentJobsScreen @Inject constructor() : IRecruitmentJobsScreen {

    @SuppressLint("NotConstructor")
    @Composable
    override fun RecruitmentJobsScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit
    ) {
        RecruitmentJobsScreenContent(
            onBackPressed = { navController.popBackStack() }
        )
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun RecruitmentJobsScreenContent(
        onBackPressed: () -> Unit = {},
    ) {
        val scope = rememberCoroutineScope()
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

        val content: @Composable (ColumnScope.(items: List<RecruitmentJob>) -> Unit) = { jobs ->
            RecruitmentJobsList(
                jobs = jobs
            ) {
                scope.launch {
                    scaffoldState.customBottomSheetState.expand()
                }
            }
        }

        BackHandler(enabled = true) {
            Timber.d("Current state - ${scaffoldState.customBottomSheetState.currentValue}")
            if (scaffoldState.customBottomSheetState.isExpanded) {
                scope.launch {
                    scaffoldState.customBottomSheetState.hide()
                }
            } else {
                onBackPressed()
            }
        }

        SearchLikeScreen(
            items = listOf(
                RecruitmentJob("Kek"),
                RecruitmentJob("Kek"),
                RecruitmentJob("Kek"),
            ),
            scaffoldState = scaffoldState,
            userName = stringResource(uiKitComponentsR.string.default_user_name), // TODO?
            mainTitle = stringResource(R.string.recruitment_job_positions),
            sheetContent = {
                RecruitmentJobsSheet(
                    jobLink = "some cool link"
                )
            },
            mainListContent = content,
            searchResultListContent = content,
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun RecruitmentJobsScreenPreview() {
        RecruitmentJobsScreenContent()
    }
}
