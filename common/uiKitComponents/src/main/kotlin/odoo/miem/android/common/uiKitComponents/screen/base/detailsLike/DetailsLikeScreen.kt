package odoo.miem.android.common.uiKitComponents.screen.base.detailsLike

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch
import odoo.miem.android.common.uiKitComponents.appbars.SimpleLogoAppBar
import odoo.miem.android.common.uiKitComponents.screen.base.detailsLike.components.DetailedInfoType
import odoo.miem.android.common.uiKitComponents.screen.base.detailsLike.components.DividedListType
import odoo.miem.android.common.uiKitComponents.screen.base.detailsLike.components.PagesType
import odoo.miem.android.common.uiKitComponents.screen.base.detailsLike.components.TextType
import odoo.miem.android.common.uiKitComponents.screen.base.detailsLike.components.bottomSheet.DetailsBottomSheetBuilder
import odoo.miem.android.common.uiKitComponents.screen.base.detailsLike.components.header.ProfileHeader
import odoo.miem.android.common.uiKitComponents.screen.base.detailsLike.components.pages.DetailedInfoPage
import odoo.miem.android.common.uiKitComponents.screen.base.detailsLike.components.pages.DividedListPage
import odoo.miem.android.common.uiKitComponents.screen.base.detailsLike.components.pages.TextPage
import odoo.miem.android.common.uiKitComponents.screen.base.detailsLike.models.DetailsLikeHeader
import timber.log.Timber
import java.util.*

/**
 * [DetailsLikeScreen] is template screen for cases of list smt and search
 *
 * @author Vorozhtcov Mikhail
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailsLikeScreen(
    header: DetailsLikeHeader,
    pages: List<PagesType> = emptyList(),
    navigateBack: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    var currentBottomSheet: DividedListType? by remember {
        mutableStateOf(null)
    }
    val onSheetExpand: (onOpen: Boolean, type: DividedListType?) -> Unit = { onOpen, type ->
        currentBottomSheet = type
        coroutineScope.launch {
            if (onOpen) {
                bottomSheetState.expand()
            } else {
                bottomSheetState.collapse()
            }
        }
    }

    BottomSheetScaffold(
        scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState),
        sheetContent = {
            currentBottomSheet?.let { type ->
                DetailsBottomSheetBuilder(
                    topic = type.topic,
                    elements = type.sheetElements,
                    onCancel = { onSheetExpand(false, null) },
                    onDone = { elements ->
                        onSheetExpand(true, null)
                        Timber.d("New data - ${elements.map { it.result }}") // TODO Delete mocks
                    }
                )
            }
        },
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        PagerContent(
            header = header,
            pages = pages,
            onSheetExpand = onSheetExpand,
            navigateBack = navigateBack
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun PagerContent(
    header: DetailsLikeHeader,
    pages: List<PagesType>,
    onSheetExpand: (onOpen: Boolean, type: DividedListType) -> Unit = { _, _ -> },
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
                    onSheetExpand = { onSheetExpand(it, type) }
                )

                else -> { /* Ignore */ }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFF9F9F9)
private fun DetailsScreenPreview() {
    DetailsLikeScreen(
        header = DetailsLikeHeader(
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
                            number = 0.0
                        )
                    )
                )
            ),
            TextType(
                topic = "Application Summary",
                text = "Some cool application summary"
            )
        )
    )
}
