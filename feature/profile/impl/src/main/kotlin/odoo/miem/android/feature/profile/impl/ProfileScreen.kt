package odoo.miem.android.feature.profile.impl

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import odoo.miem.android.common.uiKitComponents.appbars.SimpleLogoAppBar
import odoo.miem.android.common.uiKitComponents.stateholder.StateHolder
import odoo.miem.android.common.uiKitComponents.text.*
import odoo.miem.android.feature.profile.api.IProfileScreen
import odoo.miem.android.feature.profile.impl.components.ProfileHeader
import odoo.miem.android.feature.profile.impl.components.pages.*
import odoo.miem.android.feature.profile.impl.data.Contract
import odoo.miem.android.feature.profile.impl.data.Job
import odoo.miem.android.feature.profile.impl.data.User
import java.util.*
import javax.inject.Inject

/**
 * [ProfileScreen] - implementation of [IProfileScreen]
 *
 * @author Vorozhtsov Mikhail
 */
class ProfileScreen @Inject constructor() : IProfileScreen {

    @SuppressLint("NotConstructor")
    @Composable
    override fun ProfileScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit
    ) {

        StateHolder(
            isLoading = false,
            isSuccess = true
        ) {
            ProfileScreenContent(
                // TODO Delete test data
                user = User(
                    name = "",
                    email = "",
                    phone = "",
                    job = Job(
                        appliedJobName = "УЛ СВТ",
                        department = "",
                        recruiterProject = "",
                        group = "",
                        tags = "",
                        recruiter = "Королев Денис Александрович",
                        hireDate = Date(),
                        appreciation = 2.0,
                        source = "",
                        testTask = ""
                    ),
                    contract = Contract(
                        expectedSalary = 0.0F,
                        proposedSalary = 0.0F
                    ),
                    applicationSummary = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus consequat est. Praesent viverra nisl felis, eget pharetra mauris bibendum ac. Sed justo orci, blandit vehicula vestibulum quis, interdum in eros. Sed a eros luctus risus pharetra consequat. Vivamus mollis a lectus quis elementum. Integer vel nibh at nulla faucibus consequat. Morbi placerat tortor ut orci mattis, ut dapibus risus porta. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Cras luctus tempus est ut malesuada.",
                ),
                navigateBack = navController::popBackStack
            )
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    private fun ProfileScreenContent(
        user: User,
        pages: List<Pages> = Pages.values().asList(),
        navigateBack: () -> Unit = {}
    ) = Column(
        modifier = Modifier.fillMaxSize()
    ) {

        val pagerState = rememberPagerState()

        SimpleLogoAppBar(
            onBackButtonClick = navigateBack
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            item {
                ProfileHeader(
                    userName = user.name,
                    userEmail = user.email,
                    userPhone = user.phone,
                    navigateBack = navigateBack
                )

                Spacer(modifier = Modifier.height(24.dp))

                val haptic = LocalHapticFeedback.current
                var startTransaction by remember { mutableStateOf(false) }

                LaunchedEffect(pagerState) {
                    snapshotFlow { pagerState.currentPage }.collect {
                        if (startTransaction) {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        } else {
                            startTransaction = true
                        }
                    }
                }

                HorizontalPager(
                    count = pages.size,
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth()
                ) { index ->
                    when (pages[index]) {
                        Pages.JOB -> JobPage(user = user)
                        Pages.APPLICATION_SUMMARY -> ApplicationSummaryPage(user = user)
                        Pages.LOG_NOTE -> LogNotePage()
                        Pages.SCHEDULE_ACTIVITY -> ScheduleActivityPage()
                    }
                }
            }

            item {
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    activeColor = MaterialTheme.colorScheme.onTertiary,
                    inactiveColor = MaterialTheme.colorScheme.tertiary
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }

    @Composable
    @Preview(showBackground = true, backgroundColor = 0xFFF9F9F9)
    private fun ProfileScreenPreview() {

        ProfileScreenContent(
            // TODO Refactor
            user = User(
                name = "",
                email = "",
                phone = "",
                job = Job(
                    appliedJobName = "УЛ СВТ",
                    department = "",
                    recruiterProject = "",
                    group = "",
                    tags = "",
                    recruiter = "Королев Денис Александрович",
                    hireDate = Date(),
                    appreciation = 2.0,
                    source = "",
                    testTask = ""
                ),
                contract = Contract(
                    expectedSalary = 0.0F,
                    proposedSalary = 0.0F
                ),
                applicationSummary = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus consequat est. Praesent viverra nisl felis, eget pharetra mauris bibendum ac. Sed justo orci, blandit vehicula vestibulum quis, interdum in eros. Sed a eros luctus risus pharetra consequat. Vivamus mollis a lectus quis elementum. Integer vel nibh at nulla faucibus consequat. Morbi placerat tortor ut orci mattis, ut dapibus risus porta. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Cras luctus tempus est ut malesuada.",
            ),
        )
    }
}