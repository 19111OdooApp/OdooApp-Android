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
    fun testUiTextFieldsAndButtons() {
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

        // Preparation
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

        // Asserting

        // checking if all ui elements are displayed
        odooLogoNode.assertExists()
        headerNode.assertExists()
        mainTextNode.assertExists()
        serverInputNode.assertExists()
        emailInputNode.assertExists()
        passwordInputNode.assertExists()
        loginButtonNode.assertExists()
        loginWithHseButtonNode.assertExists()

        // Odoo server text field
        // text field already has some text, so we try to clean it
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

        // text input
        serverInputNode.performTextInput(testInput)
        serverInputNode.assert(hasText(testInput))
        serverInputNode.onChild().assertExists()

        // Email text field
        // text input
        emailInputNode.performTextInput(testInput)
        emailInputNode.assert(hasText(testInput))
        emailInputNode.assert(
            hasAnyChild(
                hasContentDescription(trailingIconDesc)
            )
        )

        // trying to clean text field
        emailInputNode
            .onChild()
            .assertExists()
            .performClick()
            .assertDoesNotExist()
        emailInputNode.assert(hasText(""))

        // Password text field
        // text input
        passwordInputNode.performTextInput(testInput)
        passwordInputNode.assert(hasText(
            String(CharArray(testInput.length) { '•' } )
        ))
        passwordInputNode.assert(
            hasAnyChild(
                hasContentDescription(trailingIconDesc)
            )
        )

        // trying to clean text field
        passwordInputNode
            .onChild()
            .assertExists()
            .performClick()
            .assertDoesNotExist()
        passwordInputNode.assert(hasText(""))

        // checking vanishing of login buttons after pressing it
        serverInputNode.performTextInput(testInput)
        emailInputNode.performTextInput(testInput)
        passwordInputNode.performTextInput(testInput)

        loginButtonNode.performClick()
        loginButtonNode.assertIsNotDisplayed()
        loginWithHseButtonNode.assertIsNotDisplayed()
    }
}