package odoo.miem.android.feature.selectingModules.impl

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import odoo.miem.android.common.uiKitComponents.appbars.SimpleLogoAppBar
import odoo.miem.android.common.uiKitComponents.text.TitleText
import odoo.miem.android.common.uiKitComponents.textfields.SearchTextField
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.feature.selectingModules.api.ISelectingModulesScreen
import odoo.miem.android.feature.selectingModules.impl.components.CustomBottomSheetScaffold
import odoo.miem.android.feature.selectingModules.impl.components.CustomBottomSheetValue
import odoo.miem.android.feature.selectingModules.impl.components.SelectingModulesFavoriteList
import odoo.miem.android.feature.selectingModules.impl.components.SelectingModulesHeader
import odoo.miem.android.feature.selectingModules.impl.components.rememberCustomBottomSheetScaffoldState
import odoo.miem.android.feature.selectingModules.impl.components.rememberCustomBottomSheetState
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

        // TODO Create base with loading handling
        SelectingModulesScreenContent(
            // TODO Delete Test Data
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

    @OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialApi::class)
    @Composable
    private fun SelectingModulesScreenContent(
        favoriteModules: List<OdooModule> = emptyList()
    ) {
        val topRadius = 35.dp

        val sheetState = rememberCustomBottomSheetState(
            initialValue = CustomBottomSheetValue.Collapsed
        )
        val scaffoldState = rememberCustomBottomSheetScaffoldState(customBottomSheetState = sheetState)


        CustomBottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContent = {
                val topPadding = 24.dp

                Spacer(modifier = Modifier.height(topPadding))

                SelectingModulesBottomSheetHeader()

                Spacer(modifier = Modifier.height(topPadding))

                SelectingModulesBottomSheetGrid()
            },
            sheetShape = RoundedCornerShape(
                topStart = topRadius,
                topEnd = topRadius
            ),
            sheetPeekHeight = (LocalConfiguration.current.screenHeightDp * 0.25F).dp,
            sheetElevation = 8.dp,
            backgroundColor = MaterialTheme.colorScheme.background,
            sheetBackgroundColor = MaterialTheme.colorScheme.background,
            halfCoefficient = 0.65F,
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
        ) {
            SelectingModulesMainContent(
                favoriteModules = favoriteModules
            )
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    private fun SelectingModulesMainContent(
        favoriteModules: List<OdooModule> = emptyList()
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
                if (startTransaction)
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                else
                    startTransaction = true
            }
        }

        val topPadding = 24.dp


        SimpleLogoAppBar()

        SelectingModulesHeader(
            // TODO Add data
        )

        Spacer(modifier = Modifier.height(topPadding))

        TitleText(
            textRes = R.string.choose_module_text,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = mainHorizontalPadding)
        )

        Spacer(modifier = Modifier.height(topPadding * 0.4F))

        // TODO Search Transaction
        // https://github.com/mxalbert1996/compose-shared-elements
        // https://github.com/mobnetic/compose-shared-element
        SearchTextField(
            value = searchInput,
            onValueChange = { searchInput = it },
            enabled = false
        )

        Spacer(modifier = Modifier.height(topPadding * 1.4F))

        // TODO Big Cards
        // Gradient - https://semicolonspace.com/jetpack-compose-circle-animation-gradient/

        SelectingModulesFavoriteList(
            favoriteModules = favoriteModules,
            indicatorModifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = mainHorizontalPadding)
        )
    }

    @Composable
    private fun ColumnScope.SelectingModulesBottomSheetHeader() {
        Divider(
            modifier = Modifier
                .width((LocalConfiguration.current.screenWidthDp / 8).dp)
                .align(Alignment.CenterHorizontally),
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.height(14.dp))

        TitleText(
            textRes = R.string.all_modules,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            isLarge = false
        )
    }

    @Composable
    private fun ColumnScope.SelectingModulesBottomSheetGrid(

    ) = LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize()
    ) {
        // TODO
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
