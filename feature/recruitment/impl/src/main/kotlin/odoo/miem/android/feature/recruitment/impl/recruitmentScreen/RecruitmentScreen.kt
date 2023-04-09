package odoo.miem.android.feature.recruitment.impl.recruitmentScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import odoo.miem.android.common.uiKitComponents.progressbar.LoadingScreen
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.RecruitmentLikeScreen
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.DeadlineStatus
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.core.utils.rx.collectAsState
import odoo.miem.android.core.utils.state.Result
import odoo.miem.android.core.utils.state.SuccessResult
import odoo.miem.android.core.utils.state.subscribeOnError
import odoo.miem.android.feature.navigation.api.data.Routes
import odoo.miem.android.feature.recruitment.api.IRecruitmentScreen
import odoo.miem.android.feature.recruitment.impl.RecruitmentViewModel
import odoo.miem.android.feature.recruitment.impl.data.Employee
import odoo.miem.android.feature.recruitment.impl.data.Status
import javax.inject.Inject

class RecruitmentScreen @Inject constructor() : IRecruitmentScreen {

    @Composable
    override fun RecruitmentScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit
    ) {
        val viewModel: RecruitmentViewModel = viewModel()
        val statusList by viewModel.statusState.collectAsState()
        val createStatusPictures by viewModel.picturesState.collectAsState()
        statusList.subscribeOnError(showMessage)
        val onNavigateToModulesPressed = {
            navController.navigate(Routes.selectingModules)
        }
        RecruitmentDecideOnLoading(
            statusList = statusList,
            fetchLambda = viewModel::fetchStatusList,
            onNavigateToModulesPressed = onNavigateToModulesPressed,
            onStatusClick = viewModel::changeEmployeeStatus,
            onNewStatusCreated = viewModel::createNewStatus,
            createStatusPictures = createStatusPictures.data ?: emptyList()
        )
    }

    @Composable
    private fun RecruitmentDecideOnLoading(
        statusList: Result<List<Status>>,
        fetchLambda: () -> Unit,
        onNavigateToModulesPressed: () -> Unit,
        onStatusClick: (Employee, Status) -> Unit,
        onNewStatusCreated: (String, String) -> Unit,
        createStatusPictures: List<String>
    ) {
        if (statusList is SuccessResult) {
            statusList.data?.let {
                RecruitmentLikeScreen(
                    statusList = it,
                    onNavigateToModulesPressed = onNavigateToModulesPressed,
                    onStatusClick = onStatusClick,
                    onNewStatusCreated = onNewStatusCreated,
                    createStatusPictures = createStatusPictures,
                )
            }
        } else {
            LoadingScreen(fetchLambda)
        }
    }

    @Suppress("MagicNumber")
    @Preview(showBackground = true)
    @Composable
    fun RecruitmentScreenContentPreview() {
        OdooMiemAndroidTheme {
            RecruitmentLikeScreen(
                statusList =
                listOf(
                    Status(
                        "1",
                        listOf(
                            Employee(
                                "anna",
                                2.0,
                                null,
                                null,
                                DeadlineStatus.NO_TASKS
                            )
                        ),
                        null
                    ),
                    Status(
                        "2",
                        listOf(
                            Employee(
                                "alex",
                                2.0,
                                null,
                                null,
                                DeadlineStatus.NO_TASKS
                            ),
                            Employee(
                                "misha",
                                3.0,
                                null,
                                null,
                                DeadlineStatus.NO_TASKS
                            ),
                            Employee(
                                "alex",
                                2.0,
                                null,
                                null,
                                DeadlineStatus.NO_TASKS
                            ),
                            Employee(
                                "misha",
                                3.0,
                                null,
                                null,
                                DeadlineStatus.NO_TASKS
                            ),
                            Employee(
                                "alex",
                                2.0,
                                null,
                                null,
                                DeadlineStatus.EXPIRED
                            ),
                            Employee(
                                "misha",
                                3.0,
                                null,
                                null,
                                DeadlineStatus.ACTIVE
                            ),
                            Employee(
                                "alex",
                                2.0,
                                null,
                                null,
                                DeadlineStatus.NO_TASKS
                            ),
                            Employee(
                                "misha",
                                3.0,
                                null,
                                null,
                                DeadlineStatus.NO_TASKS
                            )
                        ),
                        null
                    )
                ),
                onNavigateToModulesPressed = {},
                onStatusClick = { e, s -> },
                onNewStatusCreated = { s: String, s2: String -> },
                createStatusPictures = listOf()
            )
        }
    }
}