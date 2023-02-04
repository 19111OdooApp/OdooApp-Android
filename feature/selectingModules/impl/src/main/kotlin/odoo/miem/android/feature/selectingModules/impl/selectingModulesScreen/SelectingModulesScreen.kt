package odoo.miem.android.feature.selectingModules.impl.selectingModulesScreen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.mxalbert.sharedelements.FadeMode
import com.mxalbert.sharedelements.MaterialContainerTransformSpec
import com.mxalbert.sharedelements.SharedElement
import com.mxalbert.sharedelements.SharedElementsRoot
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import odoo.miem.android.common.network.selectingModules.api.entities.OdooModule
import odoo.miem.android.common.uiKitComponents.appbars.SimpleLogoAppBar
import odoo.miem.android.common.uiKitComponents.bottomsheet.CustomBottomSheetScaffold
import odoo.miem.android.common.uiKitComponents.bottomsheet.CustomBottomSheetValue
import odoo.miem.android.common.uiKitComponents.bottomsheet.rememberCustomBottomSheetScaffoldState
import odoo.miem.android.common.uiKitComponents.bottomsheet.rememberCustomBottomSheetState
import odoo.miem.android.common.uiKitComponents.cards.SmallModuleCard
import odoo.miem.android.common.uiKitComponents.text.SubtitleText
import odoo.miem.android.common.uiKitComponents.text.TitleText
import odoo.miem.android.common.uiKitComponents.textfields.SearchTextField
import odoo.miem.android.common.uiKitComponents.utils.SharedElementConstants
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.mainVerticalPadding
import odoo.miem.android.core.utils.rx.collectAsState
import odoo.miem.android.core.utils.state.SuccessResult
import odoo.miem.android.core.utils.state.subscribeOnError
import odoo.miem.android.feature.navigation.api.data.Routes
import odoo.miem.android.feature.selectingModules.api.ISelectingModulesScreen
import odoo.miem.android.feature.selectingModules.impl.R
import odoo.miem.android.feature.selectingModules.impl.SelectingModulesViewModel
import odoo.miem.android.feature.selectingModules.impl.searchScreen.SearchModulesScreen
import odoo.miem.android.feature.selectingModules.impl.selectingModulesScreen.components.SelectingModulesFavoriteList
import odoo.miem.android.feature.selectingModules.impl.selectingModulesScreen.components.SelectingModulesHeader
import javax.inject.Inject

/**
 * [SelectingModulesScreen] implementation of [ISelectingModulesScreen]
 *
 * Methods by purpose:
 * - [SelectingModulesScreen] - the input point to this screen is needed for initial initialization.
 * For example, ViewModel initialization
 * - [SelectingModulesScreenContent] - directly layout of this screen
 * - [SelectingModulesScreenPreview] - preview of the layout that turned out in [SelectingModulesScreenContent]
 *
 * @author Vorozhtsov Mikhail
 */
class SelectingModulesScreen @Inject constructor() : ISelectingModulesScreen {

    @SuppressLint("NotConstructor")
    @Composable
    override fun SelectingModulesScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit
    ) {
        val viewModel: SelectingModulesViewModel = viewModel()

        val userInfoState by viewModel.userInfoState.collectAsState()
        userInfoState.subscribeOnError(showMessage)

        val modulesState by viewModel.modulesState.collectAsState()
        modulesState.subscribeOnError(showMessage)

        if (userInfoState is SuccessResult) {
            viewModel.getUserModules(userInfoState.data?.uid!!)
        }

        LaunchedEffect(Unit) {
            viewModel.getUserInfo()
        }

        // TODO implementation based on some meta-information about implemented modules
        val onModuleCardClick = { navController.navigate(Routes.moduleNotFound) }

        // TODO Delete Test Data
        val modules = listOf(
            OdooModule(
                id = -1,
                parentId = null,
                childModules = mutableListOf(),
                name = "CRM",
                numberOfNotifications = 1
            ),
            OdooModule(
                id = -1,
                parentId = null,
                childModules = mutableListOf(),
                name = "Recruitment",
                numberOfNotifications = 5,
                isFavourite = true
            ),
            OdooModule(
                id = -1,
                parentId = null,
                childModules = mutableListOf(),
                name = "Pricing",
                numberOfNotifications = 123
            ),
        )

        Crossfade(
            targetState = modulesState
        ) { state ->
            if (state is SuccessResult) {
                SelectingModulesScreenContent(
                    userName = userInfoState.data?.name,
                    allModules = state.data ?: emptyList(),
                    favoriteModules = state.data?.filter { it.isFavourite } ?: emptyList(),
                    onModuleCardClick = onModuleCardClick
                )
            } else {
                SelectingModulesSplashScreen()
            }
        }
    }

    @Composable
    private fun SelectingModulesSplashScreen() = Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        var isWelcomeTextVisible by rememberSaveable { mutableStateOf(false) }
        var isLoaderVisible by rememberSaveable { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            isWelcomeTextVisible = true
            delay(1000)
            isLoaderVisible = true
        }

        AnimatedVisibility(
            visible = isWelcomeTextVisible,
            enter = expandVertically(
                animationSpec = tween(
                    durationMillis = 1000
                ),
                expandFrom = Alignment.Top
            ),
            exit = shrinkVertically(
                animationSpec = tween(
                    durationMillis = 1000
                ),
                shrinkTowards = Alignment.Bottom
            )
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(odoo.miem.android.common.uiKitComponents.R.drawable.logo_odoo),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(192.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(mainVerticalPadding * 10))

        AnimatedVisibility(
            visible = isWelcomeTextVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )
        }
    }

    @OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialApi::class)
    @Composable
    private fun SelectingModulesScreenContent(
        userName: String? = null,
        allModules: List<OdooModule> = emptyList(),
        favoriteModules: List<OdooModule> = emptyList(),
        onModuleCardClick: () -> Unit = {}
    ) {
        val topRadius = 35.dp

        val sheetState = rememberCustomBottomSheetState(
            initialValue = CustomBottomSheetValue.Collapsed
        )
        val scaffoldState = rememberCustomBottomSheetScaffoldState(
            customBottomSheetState = sheetState
        )

        val scope = rememberCoroutineScope()
        val onAddModuleCardClick: () -> Unit = {
            scope.launch {
                scaffoldState.customBottomSheetState.upToHalf()
            }
        }

        var isSearchScreenVisible by remember { mutableStateOf(false) }

        SharedElementsRoot {
            Crossfade(
                targetState = isSearchScreenVisible,
                animationSpec = tween(durationMillis = SharedElementConstants.transitionDurationMills)
            ) { visible ->
                if (!visible) {
                    CustomBottomSheetScaffold(
                        scaffoldState = scaffoldState,
                        sheetContent = {
                            Spacer(modifier = Modifier.height(24.dp))

                            SelectingModulesBottomSheetHeader()

                            Spacer(modifier = Modifier.height(6.dp))

                            SelectingModulesBottomSheetGrid(
                                allModules = allModules,
                                onModuleCardClick = onModuleCardClick
                            )
                        },
                        sheetShape = RoundedCornerShape(
                            topStart = topRadius,
                            topEnd = topRadius
                        ),
                        sheetPeekHeight = (
                            LocalConfiguration.current.screenHeightDp * SHEET_PEEK_HEIGHT_COEFFICIENT
                            ).dp,
                        sheetElevation = 8.dp,
                        backgroundColor = MaterialTheme.colorScheme.background,
                        sheetBackgroundColor = MaterialTheme.colorScheme.background,
                        halfCoefficient = HALF_COEFFICIENT,
                        modifier = Modifier
                            .fillMaxSize()
                            .imePadding()
                    ) {
                        SelectingModulesMainContent(
                            userName = userName,
                            favouriteModules = favoriteModules,
                            onModuleCardClick = onModuleCardClick,
                            onAddModuleCardClick = onAddModuleCardClick,
                            onSearchBarClick = { isSearchScreenVisible = true }
                        )
                    }
                } else {
                    SearchModulesScreen(
                        allModules = allModules,
                        favouriteModules = favoriteModules,
                        onModuleCardClick = onModuleCardClick,
                        onBackPressed = { isSearchScreenVisible = false }
                    )
                }
            }
        }
    }

    @Suppress("MagicNumber")
    @OptIn(ExperimentalPagerApi::class)
    @Composable
    private fun SelectingModulesMainContent(
        userName: String? = null,
        favouriteModules: List<OdooModule> = emptyList(),
        onModuleCardClick: () -> Unit = {},
        onAddModuleCardClick: () -> Unit = {},
        onSearchBarClick: () -> Unit = {}
    ) = Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TODO DELETE
        val pagerState = rememberPagerState()
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

        val baseTopPadding = 24.dp
        val searchTextFieldTopPadding = baseTopPadding * 0.4F
        val selectingFavoriteModulesTopPadding = baseTopPadding * 1.4F

        SimpleLogoAppBar()

        SelectingModulesHeader(
            userName = userName ?: stringResource(R.string.default_user_name)
        )

        Spacer(modifier = Modifier.height(baseTopPadding))

        TitleText(
            textRes = R.string.choose_module_text,
            isLarge = false,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = mainHorizontalPadding)
        )

        Spacer(modifier = Modifier.height(searchTextFieldTopPadding))

        SharedElement(
            key = stringResource(R.string.search_bar_key),
            screenKey = stringResource(R.string.select_modules_screen_key),
            transitionSpec = MaterialContainerTransformSpec(
                durationMillis = SharedElementConstants.transitionDurationMills,
                fadeMode = FadeMode.Out
            )
        ) {
            SearchTextField(
                enabled = false,
                value = TextFieldValue(),
                modifier = Modifier.clickable { onSearchBarClick() }
            )
        }

        Spacer(modifier = Modifier.height(selectingFavoriteModulesTopPadding))

        SelectingModulesFavoriteList(
            favoriteModules = favouriteModules,
            onModuleCardClick = onModuleCardClick,
            onAddModuleCardClick = onAddModuleCardClick,
            indicatorModifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = mainHorizontalPadding),
        )
    }

    @Composable
    private fun ColumnScope.SelectingModulesBottomSheetHeader() {
        val width = (LocalConfiguration.current.screenWidthDp * DIVIDER_WIDTH_COEFFICIENT).dp

        Divider(
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .width(width)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(14.dp))

        SubtitleText(
            textRes = R.string.all_modules,
            isLarge = true,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
    }

    @Composable
    private fun ColumnScope.SelectingModulesBottomSheetGrid(
        allModules: List<OdooModule> = emptyList(),
        onModuleCardClick: () -> Unit = {}
    ) = LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(mainHorizontalPadding / 2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(allModules) {
            var isLikedState by remember { mutableStateOf(it.isFavourite) }

            SmallModuleCard(
                moduleName = it.name,
                isLiked = isLikedState,
                onClick = onModuleCardClick,
                onLikeClick = { isLikedState = !isLikedState }
            )
        }
    }

    @Composable
    @Preview(showBackground = true, backgroundColor = 0xFFFFFF)
    private fun SelectingModulesScreenPreview() = OdooMiemAndroidTheme {
//        SelectingModulesScreenContent(
//            favoriteModules = listOf(
//                OdooModule(
//                    id = -1,
//                    parentId = null,
//                    childModules = mutableListOf(),
//                    name = "CRM",
//                    numberOfNotifications = 1
//                ),
//                OdooModule(
//                    id = -1,
//                    parentId = null,
//                    childModules = mutableListOf(),
//                    name = "Recruitment",
//                    numberOfNotifications = 5,
//                    isFavourite = true
//                ),
//                OdooModule(
//                    id = -1,
//                    parentId = null,
//                    childModules = mutableListOf(),
//                    name = "Pricing",
//                    numberOfNotifications = 123
//                )
//            )
//        )
        SelectingModulesSplashScreen()
    }

    companion object {
        private const val SHEET_PEEK_HEIGHT_COEFFICIENT = 0.25F
        private const val HALF_COEFFICIENT = 0.65F
        private const val DIVIDER_WIDTH_COEFFICIENT = 0.125F
    }
}
