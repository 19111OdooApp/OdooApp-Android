package odoo.miem.android.common.uiKitComponents.screenTemplates.employeesLike.employeeDetailsLike

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import odoo.miem.android.common.uiKitComponents.appbars.SimpleLogoAppBar
import odoo.miem.android.common.uiKitComponents.screenTemplates.base.detailsLike.components.DetailedInfoType
import odoo.miem.android.common.uiKitComponents.screenTemplates.base.detailsLike.components.DividedListType
import odoo.miem.android.common.uiKitComponents.screenTemplates.base.detailsLike.components.PagesType
import odoo.miem.android.common.uiKitComponents.screenTemplates.base.detailsLike.components.TextType
import odoo.miem.android.common.uiKitComponents.screenTemplates.base.detailsLike.components.pages.DetailedInfoPage
import odoo.miem.android.common.uiKitComponents.screenTemplates.base.detailsLike.components.pages.DividedListPage
import odoo.miem.android.common.uiKitComponents.screenTemplates.base.detailsLike.components.pages.TextPage
import odoo.miem.android.common.uiKitComponents.screenTemplates.employeesLike.employeeDetailsLike.components.EmployeeProfileHeader
import odoo.miem.android.common.uiKitComponents.screenTemplates.employeesLike.employeeDetailsLike.models.EmployeeDetailsHeader
import java.util.*

/**
 * [EmployeeDetailsLikeScreen] is employee-like screen for cases of list smt and search
 *
 * @author Egor Danilov
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun EmployeeDetailsLikeScreen(
    header: EmployeeDetailsHeader,
    pages: List<PagesType> = emptyList(),
    navigateBack: () -> Unit = {}
) = Column(
    modifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .fillMaxSize()
) {
    val pagerState = rememberPagerState()

    SimpleLogoAppBar(
        onBackButtonClick = navigateBack
    )

    Spacer(modifier = Modifier.height(12.dp))

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        EmployeeProfileHeader(
            headerData = header
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

        HorizontalPagerIndicator(
            pagerState = pagerState,
            activeColor = MaterialTheme.colorScheme.onTertiary,
            inactiveColor = MaterialTheme.colorScheme.tertiary
        )

        Spacer(modifier = Modifier.height(28.dp))

        HorizontalPager(
            count = pages.size,
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { index ->
            when (val type = pages[index]) {
                is DetailedInfoType -> DetailedInfoPage(detailedInfoType = type)
                is TextType -> TextPage(textType = type)
                is DividedListType -> DividedListPage(
                    dividedListType = type,
                )
                else -> { /* Ignore */ }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFF9F9F9)
private fun DetailsScreenPreview() {
    EmployeeDetailsLikeScreen(
        header = EmployeeDetailsHeader(
            id = -1,
            name = "Arina Shoshina",
            email = "aashoshina@miem.hse.ru",
            mobilePhone = "+79013686745",
            workPhone = "+790212121",
            company = "MIEM",
            avatarLink = "https://crm.auditory.ru/web/image?model=hr.employee&id=1608&field=avatar_128",
            avatarRequestHeaders = emptyList()
        ),
        pages = listOf(
            DetailedInfoType(
                blocks = mapOf(
                    "Main Information" to listOf(
                        DetailedInfoType.TextType(
                            key = "Department",
                            text = "Professional services"
                        ),
                        DetailedInfoType.TextType(
                            key = "Studygroup",
                            text = null
                        ),
                        DetailedInfoType.TextType(
                            key = "Manager",
                            text = null
                        ),
                        DetailedInfoType.TextType(
                            key = "Coach",
                            text = null
                        ),
                    ),
                    "Work Information" to listOf(
                        DetailedInfoType.TextType(
                            key = "Work Address",
                            text = "My Company (San Francisco)\n" +
                                "250 Executive Park Blvd, Suite 3400\n" +
                                "San Francisco CA 94134\n" +
                                "United States"
                        ),
                        DetailedInfoType.TextType(
                            key = "Working Hours",
                            text = "40 hours"
                        ),
                        DetailedInfoType.TextType(
                            key = "Timezone",
                            text = "Europe/Moscow"
                        )
                    )
                )
            ),
            TextType(
                topic = "About me",
                text = "Some cool about me"
            )
        )
    )
}
