package odoo.miem.android.feature.recruitment.impl.screen.jobs

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import odoo.miem.android.common.network.recruitment.api.entities.jobs.RecruitmentJob
import odoo.miem.android.common.network.recruitment.api.entities.jobs.RecruitmentJobState
import odoo.miem.android.common.uiKitComponents.bottomsheet.CustomBottomSheetValue
import odoo.miem.android.common.uiKitComponents.bottomsheet.rememberCustomBottomSheetScaffoldState
import odoo.miem.android.common.uiKitComponents.bottomsheet.rememberCustomBottomSheetState
import odoo.miem.android.common.uiKitComponents.progressbar.LoadingScreen
import odoo.miem.android.common.uiKitComponents.screen.searchLike.SearchLikeScreen
import odoo.miem.android.common.uiKitComponents.stateholder.StateHolder
import odoo.miem.android.common.uiKitComponents.stateholder.error.ErrorScreen
import odoo.miem.android.core.utils.rx.collectAsState
import odoo.miem.android.core.utils.state.subscribeOnError
import odoo.miem.android.feature.navigation.api.data.Routes
import odoo.miem.android.feature.recruitment.api.IRecruitmentJobsScreen
import odoo.miem.android.feature.recruitment.impl.R
import odoo.miem.android.feature.recruitment.impl.RecruitmentViewModel
import odoo.miem.android.feature.recruitment.impl.screen.jobs.components.RecruitmentJobsList
import odoo.miem.android.feature.recruitment.impl.screen.jobs.components.RecruitmentJobsSheet
import timber.log.Timber
import javax.inject.Inject

/**
 * [RecruitmentJobsScreen] implementation of [IRecruitmentJobsScreen]
 *
 * @author Vorozhtsov Mikhail
 */
class RecruitmentJobsScreen @Inject constructor() : IRecruitmentJobsScreen {

    @SuppressLint("NotConstructor")
    @Composable
    override fun RecruitmentJobsScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit
    ) {
        val viewModel: RecruitmentViewModel = viewModel()

        val jobsState by viewModel.jobsState.collectAsState()

        val userInfo by viewModel.userInfoState.collectAsState()
        userInfo.subscribeOnError(showMessage)

        val focusedJob by viewModel.focusedJob.subscribeAsState(null)

        LaunchedEffect(Unit) {
            viewModel.onOpenJobs()
        }

        StateHolder(
            state = jobsState,
            loadingContent = { LoadingScreen() },
            errorContent = { ErrorScreen { viewModel.onOpenJobs() } },
            successContent = {
                RecruitmentJobsScreenContent(
                    userName = userInfo.data?.name ?: "Cool user",
                    jobs = viewModel.jobsList,
                    focusedJob = focusedJob,
                    setFocusedJob = {
                        Timber.d("New job - $it")
                        viewModel.focusedJob.onNext(it)
                    },
                    changePublicationState = viewModel::changePublicationState,
                    changeRecruitState = viewModel::changeRecruitState,
                    changeFavoriteState = viewModel::changeFavoriteState,
                    onCardClick = { navController.navigate("${Routes.recruitmentKanban}/${it.id}") },
                    onNavigateToModulesPressed = { navController.navigate(Routes.selectingModules) },
                    onUserIconClick = { navController.navigate(Routes.userProfile) },
                    onBackPressed = { navController.popBackStack() }
                )
            },
        )
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun RecruitmentJobsScreenContent(
        userName: String,
        jobs: List<RecruitmentJob> = emptyList(),
        focusedJob: RecruitmentJob? = null,
        setFocusedJob: (job: RecruitmentJob) -> Unit = {},
        changePublicationState: (job: RecruitmentJob) -> Unit = {},
        changeRecruitState: (job: RecruitmentJob) -> Unit = {},
        changeFavoriteState: (job: RecruitmentJob) -> Unit = {},
        onCardClick: (job: RecruitmentJob) -> Unit = {},
        onNavigateToModulesPressed: () -> Unit = {},
        onUserIconClick: () -> Unit = {},
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
                onLikeClick = changeFavoriteState,
                onClick = onCardClick,
                jobs = jobs
            ) {
                setFocusedJob(it)
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
            items = jobs,
            scaffoldState = scaffoldState,
            userName = userName,
            mainTitle = stringResource(R.string.recruitment_job_positions),
            sheetContent = {
                RecruitmentJobsSheet(
                    jobLink = focusedJob?.url ?: "Error url",
                    isRecruitingDone = focusedJob?.state == RecruitmentJobState.RECRUIT_DONE,
                    isPublished = focusedJob?.isPublished ?: false,
                    onRecruitClick = {
                        focusedJob?.let {
                            changeRecruitState(it)
                        }
                    },
                    onPublishedClick = {
                        focusedJob?.let {
                            changePublicationState(it)
                        }
                    },
                )
            },
            mainListContent = content,
            searchResultListContent = content,
            onNavigateToModulesPressed = onNavigateToModulesPressed,
            onUserIconClick = onUserIconClick
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun RecruitmentJobsScreenPreview() {
        RecruitmentJobsScreenContent(
            userName = "Cool user"
        )
    }
}
