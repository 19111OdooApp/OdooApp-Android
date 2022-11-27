package odoo.miem.android.feature.authorization.base.impl

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import org.junit.Rule
import org.junit.Test

/**
 * [AuthorizationScreenUITest] - UI tests for [AuthorizationScreen]
 *
 * @author Egor Danilov
 */
class AuthorizationScreenUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test_authorizationScreen_textFields_and_buttons() {
        lateinit var odooLogoDesc: String
        lateinit var headerDesc: String
        lateinit var mainTextDesc: String
        lateinit var serverTextFieldDesc: String
        lateinit var emailTextFieldDesc: String
        lateinit var passwordTextFieldDesc: String
        lateinit var loginButtonDesc: String
        lateinit var loginWithHseButtonDesc: String
        lateinit var trailingIconDesc: String
        lateinit var testInput: String

        composeTestRule.setContent {
            odooLogoDesc = stringResource(R.string.odoo_logo_desc)
            headerDesc = stringResource(R.string.login_welcome_header)
            mainTextDesc = stringResource(R.string.login_welcome_text)
            serverTextFieldDesc = stringResource(R.string.login_odoo_server)
            emailTextFieldDesc = stringResource(R.string.login_email)
            passwordTextFieldDesc = stringResource(R.string.login_password)
            loginButtonDesc = stringResource(R.string.login_button_desc)
            loginWithHseButtonDesc = stringResource(R.string.login_with_hse_button_desc)
            trailingIconDesc = stringResource(
                odoo.miem.android.common.uiKitComponents.R.string.text_field_trailing_icon_desc
            )
            testInput = stringResource(R.string.test_input)

            val navController = rememberNavController()
            OdooMiemAndroidTheme {
                AuthorizationScreen().AuthorizationScreen(navController) {}
            }
        }
        val odooLogoNode = composeTestRule.onNodeWithContentDescription(odooLogoDesc)
        val headerNode = composeTestRule.onNodeWithText(headerDesc)
        val mainTextNode = composeTestRule.onNodeWithText(mainTextDesc)
        val serverInputNode = composeTestRule.onNodeWithText(serverTextFieldDesc)
        val emailInputNode = composeTestRule.onNodeWithText(emailTextFieldDesc)
        val passwordInputNode = composeTestRule.onNodeWithText(passwordTextFieldDesc)
        val loginButtonNode = composeTestRule.onNodeWithContentDescription(loginButtonDesc)
        val loginWithHseButtonNode = composeTestRule.onNodeWithContentDescription(loginWithHseButtonDesc)

        // Preparation
        odooLogoNode.assertExists()
        headerNode.assertExists()
        mainTextNode.assertExists()
        serverInputNode.assertExists()
        emailInputNode.assertExists()
        passwordInputNode.assertExists()
        loginButtonNode.assertExists()
        loginWithHseButtonNode.assertExists()

        // Asserting

        // Odoo server text field
        serverInputNode.performTextInput(testInput)
        serverInputNode.assert(hasText(testInput))
        serverInputNode.onChild().assertExists()
        serverInputNode.assert(
            hasAnyChild(
                hasContentDescription(trailingIconDesc)
            )
        )
        serverInputNode
            .onChild()
            .assertExists()
            .performClick()
            .assertDoesNotExist()
        serverInputNode.assert(hasText(""))

        // email text field
        emailInputNode.performTextInput(testInput)
        emailInputNode.assert(hasText(testInput))
        emailInputNode.assert(
            hasAnyChild(
                hasContentDescription(trailingIconDesc)
            )
        )
        emailInputNode
            .onChild()
            .assertExists()
            .performClick()
            .assertDoesNotExist()
        emailInputNode.assert(hasText(""))

        // password text field
        passwordInputNode.performTextInput(testInput)
        passwordInputNode.assert(hasText(
            String(CharArray(testInput.length) { '•' } )
        ))
        passwordInputNode.assert(
            hasAnyChild(
                hasContentDescription(trailingIconDesc)
            )
        )
        passwordInputNode
            .onChild()
            .assertExists()
            .performClick()
            .assertDoesNotExist()
        passwordInputNode.assert(hasText(""))

        // checking login buttons vanishing after pressing it
        serverInputNode.performTextInput(testInput)
        emailInputNode.performTextInput(testInput)
        passwordInputNode.performTextInput(testInput)
        loginButtonNode.performClick()
        loginButtonNode.assertIsNotDisplayed()
        loginWithHseButtonNode.assertIsNotDisplayed()
    }
}