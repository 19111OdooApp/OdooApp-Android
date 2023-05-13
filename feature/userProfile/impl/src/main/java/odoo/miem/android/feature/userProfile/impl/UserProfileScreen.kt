package odoo.miem.android.feature.userProfile.impl

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import odoo.miem.android.common.uiKitComponents.appbars.SimpleLogoAppBar
import odoo.miem.android.common.uiKitComponents.bottomsheet.CustomBottomSheetScaffold
import odoo.miem.android.common.uiKitComponents.bottomsheet.CustomBottomSheetValue
import odoo.miem.android.common.uiKitComponents.bottomsheet.rememberCustomBottomSheetScaffoldState
import odoo.miem.android.common.uiKitComponents.bottomsheet.rememberCustomBottomSheetState
import odoo.miem.android.common.uiKitComponents.buttons.FilledTextButton
import odoo.miem.android.common.uiKitComponents.icon.ProfileIcon
import odoo.miem.android.common.uiKitComponents.text.SubtitleText
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.core.uiKitTheme.halfMainVerticalPadding
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.mainVerticalPadding
import odoo.miem.android.core.uiKitTheme.odooPrimaryDark
import odoo.miem.android.feature.navigation.api.data.Routes
import odoo.miem.android.feature.userProfile.api.IUserProfileScreen
import javax.inject.Inject

/**
 * [UserProfileScreen] - implementation of [IUserProfileScreen] interface
 *
 * Methods by its purpose:
 * - [UserProfileScreen] - entry point to this screen, which is need for initializations.
 * E.g., for viewModel initialization
 * - [UserProfileScreenContent] - high-level layout of this screen for declaring states, scaffold, etc
 * - [UserProfileScreenMainContent] - main content of the screen
 * - [UserProfileScreenBottomSheetContent] - content of this screen bottom sheet
 * - [UserProfileScreenPreview] - preview of the layout, which was done in [UserProfileScreenMainContent]
 *
 * @author Danilov Egor
 */
class UserProfileScreen @Inject constructor() : IUserProfileScreen {

    @SuppressLint("NotConstructor")
    @Composable
    override fun UserProfileScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit
    ) {
        UserProfileScreenContent(
            navigateBack = navController::popBackStack,
            onLogOutButtonClick = { navController.navigate(Routes.authorization) }
        )
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun UserProfileScreenContent(
        navigateBack: () -> Unit = {},
        onLogOutButtonClick: () -> Unit = {}
    ) {
        val bottomSheetTopRadius = 35.dp

        val bottomSheetStateValues = listOf(
            CustomBottomSheetValue.Expanded,
            CustomBottomSheetValue.Collapsed(0f),
        )

        val sheetState = rememberCustomBottomSheetState(
            initialValue = bottomSheetStateValues.first { it is CustomBottomSheetValue.Collapsed },
            possibleValues = bottomSheetStateValues,
        )
        val scaffoldState = rememberCustomBottomSheetScaffoldState(
            customBottomSheetState = sheetState,
            possibleValues = bottomSheetStateValues,
        )

        val scope = rememberCoroutineScope()

        val expandBottomSheet: () -> Unit = {
            scope.launch {
                scaffoldState.customBottomSheetState.expand()
            }
        }

        CustomBottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContent = {
                UserProfileScreenBottomSheetContent()
            },
            sheetShape = RoundedCornerShape(
                topStart = bottomSheetTopRadius,
                topEnd = bottomSheetTopRadius
            ),
            sheetPeekHeight = 0.dp,
            sheetElevation = 8.dp,
            backgroundColor = MaterialTheme.colorScheme.background,
            sheetBackgroundColor = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxSize()
                .imePadding(),
            possibleValues = scaffoldState.customBottomSheetState.possibleValues,
        ) {
            UserProfileScreenMainContent(
                navigateBack = navigateBack,
                showBottomSheet = expandBottomSheet,
                onLogOutButtonClick = onLogOutButtonClick
            )
        }
    }

    @Composable
    private fun UserProfileScreenMainContent(
        userName: String? = null,
        userEmail: String? = null,
        userPhoneNumber: String? = null,
        navigateBack: () -> Unit = {},
        showBottomSheet: () -> Unit = {},
        onLogOutButtonClick: () -> Unit = {}
    ) = Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        SimpleLogoAppBar(onBackButtonClick = navigateBack)

        Spacer(modifier = Modifier.height(100.dp))

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ProfileIcon(
                userName = userName ?: stringResource(R.string.default_user_name),
                iconSize = 98.dp,
            )

            Spacer(modifier = Modifier.height(mainVerticalPadding))

            SubtitleText(
                text = userName ?: stringResource(R.string.default_user_name),
                isLarge = true,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(halfMainVerticalPadding))

            SubtitleText(
                text = userEmail ?: stringResource(R.string.default_user_email),
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colorScheme.onTertiary
            )

            Spacer(modifier = Modifier.height(halfMainVerticalPadding))

            SubtitleText(
                text = userPhoneNumber ?: stringResource(R.string.default_user_phone_number),
                color = MaterialTheme.colorScheme.onTertiary
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = mainVerticalPadding * 2)
        ) {
            FilledTextButton(
                onClick = onLogOutButtonClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = odooPrimaryDark,
                    contentColor = Color.White
                ),
                textResource = R.string.log_out_button,
                textStyle = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = mainHorizontalPadding),
            )

            Spacer(modifier = Modifier.height(mainVerticalPadding))

            TextButton(
                onClick = showBottomSheet,
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                SubtitleText(
                    textRes = R.string.about_app,
                    color = MaterialTheme.colorScheme.onTertiary,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }

    @Composable
    private fun ColumnScope.UserProfileScreenBottomSheetContent() = Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(mainVerticalPadding))

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            SubtitleText(
                textRes = R.string.about_app,
                isLarge = true,
            )

            Spacer(modifier = Modifier.height(mainVerticalPadding))

            // TODO add description
            SubtitleText(text = "Поставьте аппрув плз")
        }
    }

    @Composable
    @Preview(showBackground = true, backgroundColor = 0xFFFFFF)
    private fun UserProfileScreenPreview() = OdooMiemAndroidTheme {
        UserProfileScreenMainContent()
    }
}
