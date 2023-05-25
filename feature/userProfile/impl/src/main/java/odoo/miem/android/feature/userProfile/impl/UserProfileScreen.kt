package odoo.miem.android.feature.userProfile.impl

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import odoo.miem.android.common.uiKitComponents.appbars.SimpleLogoAppBar
import odoo.miem.android.common.uiKitComponents.bottomsheet.CustomBottomSheetScaffold
import odoo.miem.android.common.uiKitComponents.bottomsheet.CustomBottomSheetValue
import odoo.miem.android.common.uiKitComponents.bottomsheet.rememberCustomBottomSheetScaffoldState
import odoo.miem.android.common.uiKitComponents.bottomsheet.rememberCustomBottomSheetState
import odoo.miem.android.common.uiKitComponents.buttons.FilledTextButton
import odoo.miem.android.common.uiKitComponents.icon.ProfileIcon
import odoo.miem.android.common.uiKitComponents.progressbar.LoadingScreen
import odoo.miem.android.common.uiKitComponents.stateholder.StateHolder
import odoo.miem.android.common.uiKitComponents.stateholder.error.ErrorScreen
import odoo.miem.android.common.uiKitComponents.text.SubtitleText
import odoo.miem.android.core.networkApi.userInfo.api.source.UserProfileResponse
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.core.uiKitTheme.halfMainVerticalPadding
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.mainVerticalPadding
import odoo.miem.android.core.uiKitTheme.odooPrimaryDark
import odoo.miem.android.core.utils.rx.collectAsState
import odoo.miem.android.core.utils.state.subscribeOnError
import odoo.miem.android.feature.navigation.api.data.Routes
import odoo.miem.android.feature.userProfile.api.IUserProfileScreen
import odoo.miem.android.feature.userProfile.impl.components.UserDetailsTextItem
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
        userId: Long?,
        navController: NavHostController,
        showMessage: (Int) -> Unit
    ) {
        val viewModel: UserProfileViewModel = viewModel()

        val userProfileInfoState by viewModel.userProfileInfoState.collectAsState()
        userProfileInfoState.subscribeOnError(showMessage)

        LaunchedEffect(Unit) {
            viewModel.getUserProfile(userId)
        }

        StateHolder(
            state = userProfileInfoState,
            loadingContent = { LoadingScreen() },
            errorContent = {
                ErrorScreen(
                    isSessionExpired = it.isSessionExpired,
                    onSessionExpired = { navController.navigate(Routes.authorization) },
                    onRetry = { viewModel.getUserProfile(userId) }
                )
            },
            successContent = { profileInfo ->
                profileInfo.data?.let {
                    UserProfileScreenContent(
                        userProfileInfo = it,
                        navigateBack = navController::popBackStack,
                        onLogOutButtonClick = { navController.navigate(Routes.authorization) }
                    )
                } ?: ErrorScreen(
                    isSessionExpired = false,
                    onSessionExpired = { navController.navigate(Routes.authorization) },
                    onRetry = { viewModel.getUserProfile(userId) }
                )
            }
        )
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun UserProfileScreenContent(
        userProfileInfo: UserProfileResponse,
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
                userProfileInfo = userProfileInfo,
                navigateBack = navigateBack,
                showBottomSheet = expandBottomSheet,
                onLogOutButtonClick = onLogOutButtonClick
            )
        }
    }

    @Composable
    private fun UserProfileScreenMainContent(
        userProfileInfo: UserProfileResponse,
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
            modifier = Modifier.padding(horizontal = mainHorizontalPadding)
        ) {
            ProfileIcon(
                avatarUrl = userProfileInfo.avatar128,
                userName = userProfileInfo.name ?: stringResource(R.string.default_user_name),
                iconSize = 98.dp,
            )

            Spacer(modifier = Modifier.height(mainVerticalPadding))

            SubtitleText(
                text = userProfileInfo.name ?: stringResource(R.string.default_user_name),
                isLarge = true,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )

            userProfileInfo.email?.let {
                Spacer(modifier = Modifier.height(halfMainVerticalPadding))

                SubtitleText(
                    text = it,
                    textDecoration = TextDecoration.Underline,
                    color = MaterialTheme.colorScheme.onTertiary,
                    textAlign = TextAlign.Center
                )
            }

            userProfileInfo.mobile?.let {
                Spacer(modifier = Modifier.height(halfMainVerticalPadding))

                SubtitleText(
                    text = it,
                    color = MaterialTheme.colorScheme.onTertiary,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(mainVerticalPadding * 2))

            UserDetailsTextItem(
                key = stringResource(R.string.user_details_timezone),
                value = userProfileInfo.timezone
            )
            UserDetailsTextItem(
                key = stringResource(R.string.user_details_work_phone),
                value = userProfileInfo.workPhone
            )
            UserDetailsTextItem(
                key = stringResource(R.string.user_details_address),
                value = userProfileInfo.addressInfo?.getOrNull(1) as? String
            )
            UserDetailsTextItem(
                key = stringResource(R.string.user_details_company),
                value = userProfileInfo.companyInfo?.getOrNull(1) as? String
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
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = mainHorizontalPadding)
        ) {
            SubtitleText(
                textRes = R.string.about_app,
                isLarge = true,
            )

            Spacer(modifier = Modifier.height(mainVerticalPadding))

            SubtitleText(
                text = stringResource(R.string.credits),
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(mainVerticalPadding / 2))

            val context = LocalContext.current
            val githubUrl = stringResource(R.string.github_url)

            Text(
                text = stringResource(R.string.source_code),
                style = MaterialTheme.typography.bodyLarge.merge(
                    SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    )
                ),
                modifier = Modifier.clickable {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(githubUrl)
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.height(mainVerticalPadding / 2))
        }
    }

    @Composable
    @Preview(showBackground = true, backgroundColor = 0xFFFFFF)
    private fun UserProfileScreenPreview() = OdooMiemAndroidTheme {
        UserProfileScreenMainContent(
            userProfileInfo = UserProfileResponse(
                id = null,
                name = "Cool user",
                avatar128 = null,
                workPhone = null,
                mobile = null,
                email = null,
                timezone = null,
                companyInfo = null,
                addressInfo = null
            )
        )
    }
}
