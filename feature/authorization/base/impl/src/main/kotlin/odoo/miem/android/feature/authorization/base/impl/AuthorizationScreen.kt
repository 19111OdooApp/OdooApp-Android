package odoo.miem.android.feature.authorization.base.impl

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import odoo.miem.android.common.uiKitComponents.buttons.TextButton
import odoo.miem.android.common.uiKitComponents.buttons.buttonHorizontalPadding
import odoo.miem.android.common.uiKitComponents.buttons.loginButtonPadding
import odoo.miem.android.common.uiKitComponents.dividers.Divider
import odoo.miem.android.common.uiKitComponents.dividers.dividerVerticalPadding
import odoo.miem.android.common.uiKitComponents.textfields.LoginTextField
import odoo.miem.android.core.uiKitTheme.*
import odoo.miem.android.feature.authorization.base.api.IAuthorizationScreen

/**
 * [AuthorizationScreen] реализация интерфейса [IAuthorizationScreen]
 *
 * Методы по назначению:
 * - [AuthorizationScreen] - входная точка в этот экран, нужен для начальных инициализация.
 * Например, инициализация viewModel
 * - [AuthorizationScreenContent] - непосредственно верстка данного экрана
 * - [AuthorizationScreenPreview] - превью верстки, которая получилась в [AuthorizationScreenContent]
 *
 * @author Ворожцов Михаил, Данилов Егор
 */
class AuthorizationScreen : IAuthorizationScreen {

    @SuppressLint("NotConstructor")
    @Composable
    override fun AuthorizationScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit
    ) {
        AuthorizationScreenContent()
    }

    @Composable
    private fun AuthorizationScreenContent(

    ) = Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
    ) {
        val context = LocalContext.current
        val odooGlobalUrl = stringResource(R.string.global_odoo_url)

        var serverInput by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue(odooGlobalUrl))
        }
        var emailInput by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue())
        }
        var passwordInput by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue())
        }
        val isLoginButtonEnabled = serverInput.text.isNotEmpty()
                && emailInput.text.isNotEmpty()
                && passwordInput.text.isNotEmpty()

        Image(
            painter = painterResource(R.drawable.logo_odoo),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 30.dp)
                .size(width = 80.dp, height = 26.dp),
        )
        
        Text(
            text = stringResource(R.string.login_welcome_header),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(
                    start = mainHorizontalPadding,
                    top = 40.dp
                ),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )

        Text(
            text = stringResource(R.string.login_welcome_text),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = mainHorizontalPadding)
                .padding(top = commonPadding),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        LoginTextField(
            value = serverInput,
            labelId = R.string.login_odoo_server,
            onValueChange = {
                serverInput = it
            },
            onTrailingIconClick = {
                serverInput = TextFieldValue("")
            },
            trailingIconId = odoo.miem.android.common.uiKitComponents.R.drawable.ic_trailing_icon,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = mainHorizontalPadding)
                .padding(top = 30.dp)
        )

        LoginTextField(
            value = emailInput,
            labelId = R.string.login_email,
            onValueChange = {
                emailInput = it
            },
            onTrailingIconClick = {
                emailInput = TextFieldValue("")
            },
            trailingIconId = odoo.miem.android.common.uiKitComponents.R.drawable.ic_trailing_icon,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = mainHorizontalPadding)
                .padding(top = commonPadding)
        )

        LoginTextField(
            value = passwordInput,
            labelId = R.string.login_password,
            onValueChange = {
                passwordInput = it
            },
            onTrailingIconClick = {
                passwordInput = TextFieldValue("")
            },
            visualTransformation = PasswordVisualTransformation(),
            trailingIconId = odoo.miem.android.common.uiKitComponents.R.drawable.ic_trailing_icon,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = mainHorizontalPadding)
                .padding(top = commonPadding)
        )

        // Button's colors in preview are awful I know
        // You have to compile to watch actual ones...
        TextButton(
            onClick = { /* TODO */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = Color.White,
                disabledContainerColor = odooButtonDisabled,
                disabledContentColor = odooOnButtonDisabled
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = loginButtonPadding)
                .padding(horizontal = buttonHorizontalPadding),
            textId = R.string.login,
            isEnabled = isLoginButtonEnabled
        )

        Divider(
            textModifier = Modifier.padding(horizontal = commonPadding),
            paddingModifier = Modifier.padding(vertical = dividerVerticalPadding),
            textId = R.string.login_divider_text
        )

        TextButton(
            onClick = { /* TODO */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = hseSecondary,
                contentColor = Color.White,
            ),
            modifier = Modifier
                .padding(horizontal = buttonHorizontalPadding)
                .fillMaxWidth(),
            textId = R.string.login_hse,
            iconId = R.drawable.logo_hse,
        )
    }

    @Composable
    @Preview(showBackground = true, backgroundColor = 0xFFF9F9F9)
    private fun AuthorizationScreenPreview() = OdooMiemAndroidTheme {
        AuthorizationScreenContent()
    }
}
