package odoo.miem.android.feature.recruitment.impl.screen.jobs

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import odoo.miem.android.common.uiKitComponents.R
import odoo.miem.android.common.uiKitComponents.bottomsheet.CustomBottomSheetValue
import odoo.miem.android.common.uiKitComponents.bottomsheet.rememberCustomBottomSheetScaffoldState
import odoo.miem.android.common.uiKitComponents.bottomsheet.rememberCustomBottomSheetState
import odoo.miem.android.common.uiKitComponents.cards.BigJobCard
import odoo.miem.android.common.uiKitComponents.screen.searchLike.SearchLikeScreen
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.mainVerticalPadding
import odoo.miem.android.feature.recruitment.api.IRecruitmentJobsScreen
import odoo.miem.android.feature.recruitment.impl.screen.jobs.model.RecruitmentJob
import timber.log.Timber
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
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = mainHorizontalPadding)
                    .fillMaxSize()
            ) {
                items(jobs) {
                    BigJobCard(
                        jobName = it.name,
                        onLongClick = {
                            Timber.d("LONG CLICK!!")
                            scope.launch {
                                scaffoldState.customBottomSheetState.expand()
                            }
                        },
                        isPublished = true
                    )

                    Spacer(modifier = Modifier.height(mainVerticalPadding / 2))
                }
            }
        }

        SearchLikeScreen(
            items = listOf(
                RecruitmentJob("Kek"),
                RecruitmentJob("Kek"),
                RecruitmentJob("Kek"),
            ),
            scaffoldState = scaffoldState,
            userName = stringResource(id = R.string.default_user_name), // TODO?
            mainTitle = "Some cool title", // TODO
            sheetContent = { Box(modifier = Modifier.fillMaxSize()) }, // TODO
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
