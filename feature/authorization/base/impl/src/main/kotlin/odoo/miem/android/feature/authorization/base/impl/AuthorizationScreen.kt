package odoo.miem.android.feature.authorization.base.impl

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import odoo.miem.android.components.dividers.LoginButtonDivider
import odoo.miem.android.components.textfields.LoginTextField
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
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
 * @author Ворожцов Михаил
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
        val odooGlobalUrl = stringResource(R.string.global_odoo_url)
        var serverInput by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue(odooGlobalUrl))
        }
        var loginInput by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue())
        }
        var passwordInput by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue())
        }

        Image(
            painter = painterResource(R.drawable.logo_odoo),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 55.dp)
                .size(120.dp, 40.dp),
        )
        
        Text(
            text = stringResource(R.string.login_welcome_header),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(
                    start = 24.dp,
                    top = 41.dp
                )
        )

        Text(
            text = stringResource(R.string.login_welcome_text),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 24.dp)
                .padding(top = 11.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )

        LoginTextField(
            value = serverInput,
            labelId = R.string.login_odoo_server,
            onValueChange = {
                serverInput = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 30.dp)
        )

        LoginTextField(
            value = loginInput,
            labelId = R.string.login_email,
            onValueChange = {
                loginInput = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 10.dp)
        )

        LoginTextField(
            value = passwordInput,
            labelId = R.string.login_password,
            onValueChange = {
                passwordInput = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 10.dp)
        )

        // Button's colors in preview are awful I know
        // You have to compile to watch actual ones...
        Button(
            onClick = { /*TODO*/ },
            enabled = serverInput.text.isNotEmpty() && loginInput.text.isNotEmpty() && passwordInput.text.isNotEmpty(),
            contentPadding = PaddingValues(horizontal = 109.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            modifier = Modifier.padding(top = 40.dp)
        ) {
            Text(
                text = stringResource(R.string.login),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        LoginButtonDivider()

        Button(
            onClick = { /*TODO*/ },
            contentPadding = PaddingValues(horizontal = 56.dp,),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
        ) {
            Image(
                painter = painterResource(R.drawable.logo_hse),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = stringResource(R.string.login_hse),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }

    @Composable
    @Preview(showBackground = true, backgroundColor = 0xFFF9F9F9)
    private fun AuthorizationScreenPreview() = OdooMiemAndroidTheme {
        AuthorizationScreenContent()
    }
}
