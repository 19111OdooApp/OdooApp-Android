package odoo.miem.android.feature.selectingModules.impl.selectingModulesScreen

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
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
import kotlinx.coroutines.launch
import odoo.miem.android.common.network.selectingModules.api.entities.ImplementedModule
import odoo.miem.android.common.network.selectingModules.api.entities.OdooModule
import odoo.miem.android.common.uiKitComponents.appbars.SimpleLogoAppBar
import odoo.miem.android.common.uiKitComponents.bottomsheet.CustomBottomSheetScaffold
import odoo.miem.android.common.uiKitComponents.bottomsheet.CustomBottomSheetValue
import odoo.miem.android.common.uiKitComponents.bottomsheet.rememberCustomBottomSheetScaffoldState
import odoo.miem.android.common.uiKitComponents.bottomsheet.rememberCustomBottomSheetState
import odoo.miem.android.common.uiKitComponents.cards.SmallModuleCard
import odoo.miem.android.common.uiKitComponents.headers.CommonModuleHeader
import odoo.miem.android.common.uiKitComponents.splash.OdooSplashScreen
import odoo.miem.android.common.uiKitComponents.stateholder.StateHolder
import odoo.miem.android.common.uiKitComponents.text.SubtitleText
import odoo.miem.android.common.uiKitComponents.text.TitleText
import odoo.miem.android.common.uiKitComponents.textfields.SearchTextField
import odoo.miem.android.common.uiKitComponents.utils.SharedElementConstants
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.utils.rx.collectAsState
import odoo.miem.android.core.utils.state.SuccessResult
import odoo.miem.android.core.utils.state.subscribeOnError
import odoo.miem.android.feature.navigation.api.data.Routes
import odoo.miem.android.feature.selectingModules.api.ISelectingModulesScreen
import odoo.miem.android.feature.selectingModules.impl.R
import odoo.miem.android.feature.selectingModules.impl.SelectingModulesViewModel
import odoo.miem.android.feature.selectingModules.impl.searchScreen.SearchModulesScreen
import odoo.miem.android.feature.selectingModules.impl.selectingModulesScreen.components.SelectingModulesFavouriteList
import timber.log.Timber
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
 * @author Vorozhtsov Mikhail, Egor Danilov
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
            LaunchedEffect(Unit) {
                viewModel.getUserModules(userInfoState.data?.uid!!)
            }
        }

        LaunchedEffect(Unit) {
            viewModel.getUserInfo()
        }

        val onModuleCardClick: (OdooModule) -> Unit = {
            Timber.d("onModuleCardClick(): name - ${it.name}, isImplemented - ${it.isImplemented}")
            if (it.isImplemented) {
                when (it.name) {
                    ImplementedModule.RECRUITMENT.naming -> navController.navigate(Routes.recruitment)
                    ImplementedModule.CRM.naming -> navController.navigate(Routes.crm)
                }
            } else {
                navController.navigate(Routes.moduleNotFound)
            }
        }

        val navigateToUserProfile: () -> Unit = {
            navController.navigate(Routes.userProfile)
        }

        StateHolder(
            state = modulesState,
            loadingContent = { OdooSplashScreen() },
            successContent = {
                val allModules = viewModel.allModules
                val favouriteModules = allModules.filter { it.isFavourite }

                val performModulesSearch: (String) -> List<OdooModule> = { input ->
                    val filteredModules = modulesState.data?.let { modules ->
                        modules.filter {
                            it.name.trim().lowercase().startsWith(
                                prefix = input.trim().lowercase(),
                                ignoreCase = true
                            )
                        }
                    }

                    filteredModules ?: emptyList()
                }

                SelectingModulesScreenContent(
                    userName = userInfoState.data?.name,
                    allModules = allModules,
                    favoriteModules = favouriteModules,
                    onModuleCardClick = onModuleCardClick,
                    onLikeModuleClick = viewModel::onModuleLikeClick,
                    onSearchValueChange = performModulesSearch,
                    navigateToUserProfile = navigateToUserProfile,
                )
            }
        )
    }

    @OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialApi::class)
    @Composable
    private fun SelectingModulesScreenContent(
        userName: String? = null,
        allModules: List<OdooModule> = emptyList(),
        favoriteModules: List<OdooModule> = emptyList(),
        onModuleCardClick: (OdooModule) -> Unit = {},
        onLikeModuleClick: (OdooModule) -> Unit = {},
        onSearchValueChange: (String) -> List<OdooModule> = { emptyList() },
        navigateToUserProfile: () -> Unit = {},
    ) {
        val sheetPeekHeight = (
            LocalConfiguration.current.screenHeightDp * SHEET_PEEK_HEIGHT_COEFFICIENT
            ).dp
        val sheetPeekHeightPx = with(LocalDensity.current) { sheetPeekHeight.toPx() }

        val bottomSheetValues = listOf(
            CustomBottomSheetValue.Expanded,
            CustomBottomSheetValue.Collapsed(sheetPeekHeightPx),
            CustomBottomSheetValue.Half(
                HALF_COEFFICIENT
            )
        )
        val topRadius = 35.dp

        val sheetState = rememberCustomBottomSheetState(
            initialValue = bottomSheetValues.first { it is CustomBottomSheetValue.Collapsed },
            possibleValues = bottomSheetValues,
        )
        val scaffoldState = rememberCustomBottomSheetScaffoldState(
            customBottomSheetState = sheetState,
            possibleValues = bottomSheetValues,
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
                                onModuleCardClick = onModuleCardClick,
                                onLikeModuleClick = onLikeModuleClick
                            )
                        },
                        sheetShape = RoundedCornerShape(
                            topStart = topRadius,
                            topEnd = topRadius
                        ),
                        sheetPeekHeight = sheetPeekHeight,
                        sheetElevation = 8.dp,
                        backgroundColor = MaterialTheme.colorScheme.background,
                        sheetBackgroundColor = MaterialTheme.colorScheme.background,
                        modifier = Modifier
                            .fillMaxSize()
                            .imePadding(),
                        possibleValues = scaffoldState.customBottomSheetState.possibleValues,
                    ) {
                        SelectingModulesMainContent(
                            userName = userName,
                            favouriteModules = favoriteModules,
                            onModuleCardClick = onModuleCardClick,
                            onLikeModuleClick = onLikeModuleClick,
                            onAddModuleCardClick = onAddModuleCardClick,
                            onSearchBarClick = { isSearchScreenVisible = true },
                            navigateToUserProfile = navigateToUserProfile
                        )
                    }
                } else {
                    SearchModulesScreen(
                        allModules = allModules,
                        favouriteModules = favoriteModules,
                        onModuleCardClick = onModuleCardClick,
                        onLikeModuleClick = onLikeModuleClick,
                        onBackPressed = { isSearchScreenVisible = false },
                        onSearchValueChange = onSearchValueChange
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
        onModuleCardClick: (OdooModule) -> Unit = {},
        onLikeModuleClick: (OdooModule) -> Unit = {},
        onAddModuleCardClick: () -> Unit = {},
        onSearchBarClick: () -> Unit = {},
        navigateToUserProfile: () -> Unit = {}
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

        CommonModuleHeader(
            userName = userName ?: stringResource(R.string.default_user_name),
            onUserIconClick = navigateToUserProfile
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

        SelectingModulesFavouriteList(
            favoriteModules = favouriteModules,
            onModuleCardClick = onModuleCardClick,
            onLikeModuleClick = onLikeModuleClick,
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
        onModuleCardClick: (OdooModule) -> Unit = {},
        onLikeModuleClick: (OdooModule) -> Unit = {},
    ) = LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(mainHorizontalPadding / 2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(allModules) { module ->
            with(module) {
                SmallModuleCard(
                    moduleName = this.name,
                    iconDownloadUrl = this.iconDownloadUrl,
                    isLiked = this.isFavourite,
                    onClick = { onModuleCardClick(module) },
                    onLikeClick = {
                        onLikeModuleClick(this)
                    }
                )
            }
        }
    }

    @Composable
    @Preview(showBackground = true, backgroundColor = 0xFFFFFF)
    private fun SelectingModulesScreenPreview() = OdooMiemAndroidTheme {
        SelectingModulesScreenContent(
            favoriteModules = listOf(
                OdooModule(
                    id = -1,
                    parentId = null,
                    childModules = mutableListOf(),
                    name = "CRM",
                    iconDownloadUrl = "",
                    numberOfNotifications = 1
                ),
                OdooModule(
                    id = -1,
                    parentId = null,
                    childModules = mutableListOf(),
                    name = "Recruitment",
                    iconDownloadUrl = "",
                    numberOfNotifications = 5,
                    isFavourite = true
                ),
                OdooModule(
                    id = -1,
                    parentId = null,
                    childModules = mutableListOf(),
                    name = "Pricing",
                    iconDownloadUrl = "",
                    numberOfNotifications = 123
                )
            )
        )
    }

    companion object {
        private const val SHEET_PEEK_HEIGHT_COEFFICIENT = 0.25F
        private const val HALF_COEFFICIENT = 0.65F
        private const val DIVIDER_WIDTH_COEFFICIENT = 0.125F
    }
}
