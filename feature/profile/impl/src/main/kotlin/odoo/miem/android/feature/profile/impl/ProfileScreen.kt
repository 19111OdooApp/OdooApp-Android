package odoo.miem.android.feature.profile.impl

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import odoo.miem.android.common.uiKitComponents.appbars.SimpleLogoAppBar
import odoo.miem.android.common.uiKitComponents.stateholder.StateHolder
import odoo.miem.android.common.uiKitComponents.text.*
import odoo.miem.android.feature.profile.api.IProfileScreen
import odoo.miem.android.feature.profile.impl.components.*
import odoo.miem.android.feature.profile.impl.components.pages.*
import odoo.miem.android.feature.profile.impl.data.DetailsHeader
import odoo.miem.android.feature.profile.impl.data.DividedListItem
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
            // TODO Delete test data
            ProfileScreenContent(
                header = DetailsHeader(
                    title = "Arina Shoshina",
                    majorSubtitle = "aashoshina@miem.hse.ru",
                    minorSubtitle = "+79013686745"
                ),
                pages = listOf(
                    DetailedInfoType(
                        blocks = mapOf(
                            "Job" to listOf(
                                DetailedInfoType.TextType(
                                    key = "Applied Job",
                                    text = "УЛ СВТ"
                                ),
                                DetailedInfoType.TextType(
                                    key = "Department",
                                    text = ""
                                ),
                                DetailedInfoType.TextType(
                                    key = "Recruter’s project",
                                    text = ""
                                ),
                                DetailedInfoType.TextType(
                                    key = "Person’s group",
                                    text = ""
                                ),
                                DetailedInfoType.TextType(
                                    key = "Tags",
                                    text = ""
                                ),
                                DetailedInfoType.TextType(
                                    key = "Recruiter",
                                    text = "Королев Денис Александрович"
                                ),
                                DetailedInfoType.DateType(
                                    key = "Hire Date",
                                    date = Date()
                                ),
                                DetailedInfoType.RatingType(
                                    key = "Appreciation",
                                    rating = 2.0
                                ),
                                DetailedInfoType.TextType(
                                    key = "Source",
                                    text = ""
                                ),
                                DetailedInfoType.TextType(
                                    key = "Test task",
                                    text = ""
                                )
                            ),

                            "Contract" to listOf(
                                DetailedInfoType.NumberType(
                                    key = "Expected Salary",
                                    number = 0F
                                ),
                                DetailedInfoType.NumberType(
                                    key = "Proposed Salary",
                                    number = 0F
                                )
                            )
                        )
                    ),
                    TextType(
                        topic = "Application Summary",
                        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus consequat est. Praesent viverra nisl felis, eget pharetra mauris bibendum ac. Sed justo orci, blandit vehicula vestibulum quis, interdum in eros. Sed a eros luctus risus pharetra consequat. Vivamus mollis a lectus quis elementum. Integer vel nibh at nulla faucibus consequat. Morbi placerat tortor ut orci mattis, ut dapibus risus porta. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Cras luctus tempus est ut malesuada."
                    ),
                    // Log note
                    object : DividedListType {
                        override val topic: String = "Log note"

                        override val items: List<DividedListItem> = listOf(
                            DividedListItem(),
                            DividedListItem(),
                            DividedListItem(),
                            DividedListItem(),
                        )

                        override val sheetContent: ColumnScope.() -> Unit = {}

                        override val bottomSheetButtonText: String = "Add new log note"
                    },
                    object : DividedListType {
                        override val topic: String = "Schedule activity"

                        override val items: List<DividedListItem> = listOf(
                            DividedListItem(),
                            DividedListItem(),
                            DividedListItem(),
                            DividedListItem(),
                        )

                        override val sheetContent: ColumnScope.() -> Unit = {}

                        override val bottomSheetButtonText: String = "Add new schedule activity"
                    }
                ),
                navigateBack = navController::popBackStack
            )
        }
    }

    @OptIn(ExperimentalMaterialApi::class, ExperimentalPagerApi::class)
    @Composable
    private fun ProfileScreenContent(
        header: DetailsHeader,
        pages: List<PagesType> = emptyList(),
        navigateBack: () -> Unit = {}
    ) {
        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()
        val sheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            skipHalfExpanded = true
        )
        val onSheetExpand: (onOpen: Boolean) -> Unit = { onOpen ->
            coroutineScope.launch {
                if (onOpen)
                    sheetState.show()
                else
                    sheetState.hide()
            }
        }

        BackHandler(sheetState.isVisible) {
            onSheetExpand(false)
        }

        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetContent = {
                Text("sdnklnzxffsdk", modifier = Modifier.fillMaxSize()) // TODO Depends on screen
            },
            sheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            PagerContent(
                header = header,
                pages = pages,
                pagerState = pagerState,
                onSheetExpand = onSheetExpand,
                navigateBack = navigateBack
            )
        }
    }


    @OptIn(ExperimentalPagerApi::class)
    @Composable
    private fun PagerContent(
        header: DetailsHeader,
        pages: List<PagesType>,
        pagerState: PagerState,
        onSheetExpand: (onOpen: Boolean) -> Unit = {},
        navigateBack: () -> Unit = {}
    ) = Column(
        modifier = Modifier.fillMaxSize()
    ) {

        SimpleLogoAppBar(
            onBackButtonClick = navigateBack
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ProfileHeader(
                title = header.title,
                majorSubtitle = header.majorSubtitle,
                minorSubtitle = header.minorSubtitle,
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
                        onSheetExpand = onSheetExpand
                    )
                    else -> {/* Ignore */ }
                }
            }
        }
    }

    @Composable
    @Preview(showBackground = true, backgroundColor = 0xFFF9F9F9)
    private fun ProfileScreenPreview() {

        ProfileScreenContent(
            header = DetailsHeader(
                title = "Arina Shoshina",
                majorSubtitle = "aashoshina@miem.hse.ru",
                minorSubtitle = "+79013686745"
            ),
            pages = listOf(
                DetailedInfoType(
                    blocks = mapOf(
                        "Job" to listOf(
                            DetailedInfoType.TextType(
                                key = "Applied Job",
                                text = "УЛ СВТ"
                            ),
                            DetailedInfoType.TextType(
                                key = "Recruiter",
                                text = "Королев Денис Александрович"
                            ),
                            DetailedInfoType.DateType(
                                key = "Hire Date",
                                date = Date()
                            ),
                            DetailedInfoType.RatingType(
                                key = "Appreciation",
                                rating = 2.0
                            ),
                        ),

                        "Contract" to listOf(
                            DetailedInfoType.NumberType(
                                key = "Expected Salary",
                                number = 0F
                            )
                        )
                    )
                ),
                TextType(
                    topic = "Application Summary",
                    text = "Some cool application summary"
                ),
                // Log note
                object : DividedListType {
                    override val topic: String = "Log note"

                    override val items: List<DividedListItem> = listOf(
                        DividedListItem(),
                        DividedListItem(),
                        DividedListItem(),
                        DividedListItem(),
                    )

                    override val sheetContent: ColumnScope.() -> Unit = {}

                    override val bottomSheetButtonText: String = "Add new log note"
                }
            )
        )
    }
}