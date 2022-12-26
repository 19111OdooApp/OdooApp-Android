package odoo.miem.android.feature.selectingModules.impl

import android.annotation.SuppressLint
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import odoo.miem.android.common.uiKitComponents.appbars.SimpleLogoAppBar
import odoo.miem.android.common.uiKitComponents.bottomsheet.CustomBottomSheetScaffold
import odoo.miem.android.common.uiKitComponents.bottomsheet.CustomBottomSheetValue
import odoo.miem.android.common.uiKitComponents.bottomsheet.rememberCustomBottomSheetScaffoldState
import odoo.miem.android.common.uiKitComponents.bottomsheet.rememberCustomBottomSheetState
import odoo.miem.android.common.uiKitComponents.cards.SmallModuleCard
import odoo.miem.android.common.uiKitComponents.text.SubTitleText
import odoo.miem.android.common.uiKitComponents.text.TitleText
import odoo.miem.android.common.uiKitComponents.textfields.SearchTextField
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.feature.selectingModules.api.ISelectingModulesScreen
import odoo.miem.android.feature.selectingModules.impl.components.SelectingModulesFavoriteList
import odoo.miem.android.feature.selectingModules.impl.components.SelectingModulesHeader
import odoo.miem.android.feature.selectingModules.impl.data.OdooModule
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
        // TODO Delete Test Data
        val modules = listOf(
            OdooModule(
                name = "CRM",
                numberOfNotifications = 1
            ),
            OdooModule(
                name = "Recruitment",
                numberOfNotifications = 5,
                isLiked = true
            ),
            OdooModule(
                name = "Pricing",
                numberOfNotifications = 123
            ),
        )

        // TODO Create base with loading handling
        SelectingModulesScreenContent(
            allModules = modules,
            favoriteModules = modules
        )
    }

    @OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialApi::class)
    @Composable
    private fun SelectingModulesScreenContent(
        allModules: List<OdooModule> = emptyList(),
        favoriteModules: List<OdooModule> = emptyList()
    ) {
        val topRadius = 35.dp

        val sheetPeekHeightCoefficient = 0.25F
        val halfCoefficient = 0.65F

        val sheetState = rememberCustomBottomSheetState(
            initialValue = CustomBottomSheetValue.Collapsed
        )
        val scaffoldState = rememberCustomBottomSheetScaffoldState(customBottomSheetState = sheetState)

        val scope = rememberCoroutineScope()
        val onAddModuleCardClick: () -> Unit = {
            scope.launch {
                scaffoldState.customBottomSheetState.upToHalf()
            }
        }

        CustomBottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContent = {
                val topPadding = 24.dp

                Spacer(modifier = Modifier.height(topPadding))

                SelectingModulesBottomSheetHeader()
                
                Spacer(modifier = Modifier.height(6.dp))

                SelectingModulesBottomSheetGrid(allModules = allModules)
            },
            sheetShape = RoundedCornerShape(
                topStart = topRadius,
                topEnd = topRadius
            ),
            sheetPeekHeight = (LocalConfiguration.current.screenHeightDp * sheetPeekHeightCoefficient).dp,
            sheetElevation = 8.dp,
            backgroundColor = MaterialTheme.colorScheme.background,
            sheetBackgroundColor = MaterialTheme.colorScheme.background,
            halfCoefficient = halfCoefficient,
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
        ) {
            SelectingModulesMainContent(
                allModules = allModules,
                favouriteModules = favoriteModules,
                onAddModuleCardClick = onAddModuleCardClick
            )
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    private fun SelectingModulesMainContent(
        allModules: List<OdooModule> = emptyList(),
        favouriteModules: List<OdooModule> = emptyList(),
        onAddModuleCardClick: () -> Unit = {}
    ) = Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var searchInput by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue())
        }

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
            // TODO Add data
        )

        Spacer(modifier = Modifier.height(baseTopPadding))

        TitleText(
            textRes = R.string.choose_module_text,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = mainHorizontalPadding)
        )

        Spacer(modifier = Modifier.height(searchTextFieldTopPadding))

        // TODO Search Transaction
        // https://github.com/mxalbert1996/compose-shared-elements
        // https://github.com/mobnetic/compose-shared-element
        SearchTextField(
            value = searchInput,
            onValueChange = { searchInput = it },
            enabled = true
        )

        Spacer(modifier = Modifier.height(selectingFavoriteModulesTopPadding))

        SelectingModulesFavoriteList(
            favoriteModules = favouriteModules,
            indicatorModifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = mainHorizontalPadding),
            onAddModuleCardClick = onAddModuleCardClick
        )
    }

    @Composable
    private fun ColumnScope.SelectingModulesBottomSheetHeader() {
        val width = (LocalConfiguration.current.screenWidthDp / 8).dp

        Divider(
            modifier = Modifier
                .width(width)
                .align(Alignment.CenterHorizontally),
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.height(14.dp))

        SubTitleText(
            textRes = R.string.all_modules,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            isLarge = true
        )
    }

    @Composable
    private fun ColumnScope.SelectingModulesBottomSheetGrid(
        allModules: List<OdooModule> = emptyList()
    ) = LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(mainHorizontalPadding / 2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(allModules) {
            var isLikedState by remember { mutableStateOf(it.isLiked) }

            SmallModuleCard(
                moduleName = it.name,
                isLiked = isLikedState,
                onLikeClick = { isLikedState = !isLikedState }
            )
        }
    }

    @Composable
    @Preview(showBackground = true, backgroundColor = 0xFFFFFF)
    private fun SelectingModulesScreenPreview() = OdooMiemAndroidTheme {
        SelectingModulesScreenContent(
            favoriteModules = listOf(
                OdooModule(
                    name = "CRM",
                    numberOfNotifications = 1
                ),
                OdooModule(
                    name = "Recruitment",
                    numberOfNotifications = 5
                ),
                OdooModule(
                    name = "Pricing",
                    numberOfNotifications = 123
                ),
            )
        )
    }
}
