package odoo.miem.android.feature.recruitment.impl.recruitmentScreen

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import odoo.miem.android.common.uiKitComponents.progressbar.LoadingScreen
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.RecruitmentLikeScreen
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.DeadlineStatus
import odoo.miem.android.common.uiKitComponents.stateholder.StateHolder
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.core.utils.rx.collectAsState
import odoo.miem.android.core.utils.state.Result
import odoo.miem.android.core.utils.state.SuccessResult
import odoo.miem.android.core.utils.state.subscribeOnError
import odoo.miem.android.feature.navigation.api.data.Routes
import odoo.miem.android.feature.recruitment.api.IRecruitmentScreen
import odoo.miem.android.feature.recruitment.impl.R
import odoo.miem.android.feature.recruitment.impl.RecruitmentViewModel
import odoo.miem.android.feature.recruitment.impl.data.Employee
import odoo.miem.android.feature.recruitment.impl.data.Status
import javax.inject.Inject

class RecruitmentScreen @Inject constructor() : IRecruitmentScreen {

    @SuppressLint("NotConstructor")
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

        val onUserIconClick = {
            navController.navigate(Routes.userProfile)
        }

        LaunchedEffect(Unit) {
            viewModel.fetchStatusList()
        }

        StateHolder(
            state = statusList,
            loadingContent = { LoadingScreen() },
            successContent = { result ->
                result.data?.let {
                    RecruitmentLikeScreen(
                        statusList = it,
                        onNavigateToModulesPressed = onNavigateToModulesPressed,
                        onStatusClick = viewModel::changeEmployeeStatus,
                        onNewStatusCreated = viewModel::createNewStatus,
                        onUserIconClick = onUserIconClick,
                        onEmployeeCardClick = {
                            navController.navigate(Routes.details)
                        },
                        createStatusPictures = createStatusPictures.data ?: emptyList(),
                        searchHintRes = R.string.recruitment_search_hint
                    )
                }
            }
        )
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
                createStatusPictures = listOf(),
                searchHintRes = R.string.recruitment_search_hint,
            )
        }
    }
}
