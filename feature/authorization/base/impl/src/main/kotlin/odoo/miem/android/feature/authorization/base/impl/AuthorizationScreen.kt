package odoo.miem.android.feature.authorization.base.impl

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import odoo.miem.android.common.uiKitComponents.appbars.SimpleLogoAppBar
import odoo.miem.android.common.uiKitComponents.buttons.FilledTextButton
import odoo.miem.android.common.uiKitComponents.dividers.Divider
import odoo.miem.android.common.uiKitComponents.text.SubtitleText
import odoo.miem.android.common.uiKitComponents.text.TitleText
import odoo.miem.android.common.uiKitComponents.textfields.LoginTextField
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.core.uiKitTheme.commonPadding
import odoo.miem.android.core.uiKitTheme.dividerVerticalPadding
import odoo.miem.android.core.uiKitTheme.hseSecondary
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.odooGray
import odoo.miem.android.core.uiKitTheme.odooOnGray
import odoo.miem.android.core.utils.rx.collectAsState
import odoo.miem.android.core.utils.state.LoadingResult
import odoo.miem.android.core.utils.state.SuccessResult
import odoo.miem.android.core.utils.state.subscribeOnError
import odoo.miem.android.feature.authorization.base.api.IAuthorizationScreen
import odoo.miem.android.feature.authorization.base.impl.hse.HseAuthorizationScreen
import odoo.miem.android.feature.navigation.api.data.Routes
import javax.inject.Inject

/**
 * [AuthorizationScreen] - implementation of [IAuthorizationScreen] interface
 *
 * Methods by its purpose:
 * - [AuthorizationScreen] - entry point to this screen, which is need for initializations.
 * E.g., for viewModel initialization
 * - [AuthorizationScreenContent] - layout of this screen
 * - [AuthorizationScreenPreview] - preview of the layout, which was done in [AuthorizationScreenContent]
 *
 * @author Vorozhtsov Mikhail, Danilov Egor
 */
class AuthorizationScreen @Inject constructor() : IAuthorizationScreen {

    @SuppressLint("NotConstructor")
    @Composable
    override fun AuthorizationScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit,
    ) {
        val viewModel: AuthorizationViewModel = viewModel()

        val authorizationStatus by viewModel.authorizationState.collectAsState()
        authorizationStatus.subscribeOnError(showMessage)

        if (authorizationStatus is SuccessResult) {
            LaunchedEffect(Unit) {
                navController.navigate(Routes.selectingModules) {
                    popUpTo(Routes.authorization) { inclusive = true }
                }
            }
        }

        AuthorizationScreenContent(
            onGeneralAuthorization = viewModel::generalAuthorization,
            showMessage = showMessage,
            isLoading = authorizationStatus is LoadingResult || authorizationStatus is SuccessResult,
            getHseAuthorizationUrl = viewModel::generateHseAuthorizationUrl,
            exitCondition = viewModel::hseWebViewExitCondition
        )
    }

    @Composable
    private fun AuthorizationScreenContent(
        onGeneralAuthorization: (baseUrl: String, login: String, password: String) -> Unit = { _, _, _ -> },
        showMessage: (Int) -> Unit = {},
        isLoading: Boolean = false,
        getHseAuthorizationUrl: (rawUrl: String) -> String = { _ -> "" },
        exitCondition: (rawDomain: String, currentUrl: String?, cookie: String?) -> Boolean = { _, _, _ -> false }
    ) = Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
    ) {
        val loginButtonDesc = stringResource(R.string.login_button_desc)
        val loginWithHseButtonDesc = stringResource(R.string.login_with_hse_button_desc)

        var serverInput by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue())
        }
        var emailInput by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue())
        }
        var passwordInput by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue())
        }

        var isServerInputError by remember { mutableStateOf(false) }
        var isLoginInputError by remember { mutableStateOf(false) }
        var isPasswordInputError by remember { mutableStateOf(false) }
        val isLoginButtonEnabled = serverInput.text.isNotEmpty() &&
            emailInput.text.isNotEmpty() && passwordInput.text.isNotEmpty()

        val onLoginButtonClick = {
            isServerInputError = serverInput.text.isBlank()
            isLoginInputError = emailInput.text.isBlank()
            isPasswordInputError = passwordInput.text.isBlank()

            if (isServerInputError || isLoginInputError || isPasswordInputError) {
                showMessage(R.string.login_alert_message)
            } else {
                onGeneralAuthorization(
                    serverInput.text,
                    emailInput.text,
                    passwordInput.text
                )
            }
        }

        val textFieldTopPadding = 20.dp

        var showHseAuthorization by remember { mutableStateOf(false) }

        if (showHseAuthorization) {
            val wrappedExitCondition = { currentUrl: String?, cookie: String? ->
                val result = exitCondition(serverInput.text, currentUrl, cookie)

                if (result) {
                    showHseAuthorization = false
                }

                result
            }

            HseAuthorizationScreen(
                baseUrl = getHseAuthorizationUrl(serverInput.text),
                exitCondition = wrappedExitCondition,
                setInvisible = {
                    showHseAuthorization = false
                }
            )
        }

        SimpleLogoAppBar()

        TitleText(
            textRes = R.string.login_welcome_header,
            isLarge = false,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(
                    start = mainHorizontalPadding,
                    top = 10.dp
                )
        )

        SubtitleText(
            textRes = R.string.login_welcome_text,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(
                    horizontal = mainHorizontalPadding,
                    vertical = commonPadding
                )
        )

        LoginTextField(
            value = serverInput,
            labelResource = R.string.login_odoo_server,
            onValueChange = {
                isServerInputError = false
                serverInput = it
            },
            imeAction = ImeAction.Next,
            isError = isServerInputError,
            placeholder = {
                SubtitleText(
                    textRes = R.string.global_odoo_url,
                    color = Color.Gray
                )
            },
            modifier = Modifier.padding(top = textFieldTopPadding)
        )

        LoginTextField(
            value = emailInput,
            labelResource = R.string.login_email,
            onValueChange = {
                isLoginInputError = false
                emailInput = it
            },
            imeAction = ImeAction.Next,
            isError = isLoginInputError,
            modifier = Modifier.padding(top = textFieldTopPadding)
        )

        LoginTextField(
            value = passwordInput,
            labelResource = R.string.login_password,
            onValueChange = {
                isPasswordInputError = false
                passwordInput = it
            },
            visualTransformation = PasswordVisualTransformation(),
            isError = isPasswordInputError,
            modifier = Modifier.padding(top = textFieldTopPadding)
        )

        if (isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(48.dp)
                    .padding(top = 100.dp)
            )
        } else {
            FilledTextButton(
                onClick = { onLoginButtonClick() },
                isEnabled = isLoginButtonEnabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White,
                    disabledContainerColor = odooGray,
                    disabledContentColor = odooOnGray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 75.dp)
                    .padding(horizontal = 36.dp)
                    .semantics { contentDescription = loginButtonDesc },
                textResource = R.string.login
            )

            Divider(
                textModifier = Modifier.padding(horizontal = commonPadding),
                paddingModifier = Modifier.padding(vertical = dividerVerticalPadding),
                textResource = R.string.login_divider_text
            )

            FilledTextButton(
                onClick = {
                    if (serverInput.text.isEmpty()) {
                        showMessage(R.string.hse_empty_url_error)
                    } else {
                        showHseAuthorization = true
                        getHseAuthorizationUrl(serverInput.text)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = hseSecondary,
                    contentColor = Color.White,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 36.dp)
                    .semantics { contentDescription = loginWithHseButtonDesc },
                textResource = R.string.login_hse,
                iconResource = R.drawable.logo_hse,
            )
        }
    }

    @Composable
    @Preview(showBackground = true, backgroundColor = 0xFFF9F9F9)
    private fun AuthorizationScreenPreview() = OdooMiemAndroidTheme {
        AuthorizationScreenContent()
    }
}
