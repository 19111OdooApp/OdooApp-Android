package odoo.miem.android.feature.profile.impl

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import odoo.miem.android.common.uiKitComponents.stateholder.StateHolder
import odoo.miem.android.common.uiKitComponents.text.*
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.feature.profile.api.IProfileScreen
import odoo.miem.android.feature.profile.impl.components.ProfileHeader
import odoo.miem.android.feature.profile.impl.components.pages.*
import odoo.miem.android.feature.profile.impl.data.User
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
                user = User("", "", ""), // TODO Delete test data
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
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val haptic = LocalHapticFeedback.current
        val pagerState = rememberPagerState()
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

        ProfileHeader(
            userName = user.name,
            userEmail = user.email,
            userPhone = user.phone,
            navigateBack = navigateBack
        )

        HorizontalPager(
            count = pages.size,
            contentPadding = PaddingValues(horizontal = mainHorizontalPadding),
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { index ->
            when (pages[index]) {
                Pages.JOB -> JobPage()
                Pages.APPLICATION_SUMMARY -> ApplicationSummaryPage()
                Pages.LOG_NOTE -> LogNotePage()
                Pages.SCHEDULE_ACTIVITY -> ScheduleActivityPage()
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            activeColor = MaterialTheme.colorScheme.onTertiary,
            inactiveColor = MaterialTheme.colorScheme.tertiary
        )
    }

    @Composable
    private fun ProfileScreenPreview() {

        ProfileScreenContent(
            user = User("", "", "")
        )
    }
}