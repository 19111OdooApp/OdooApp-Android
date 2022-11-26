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
    fun authorizationScreen_test() {
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

            OdooMiemAndroidTheme {
                AuthorizationScreen().AuthorizationScreen(null) {}
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

        // Checking if all ui elements are displayed
        odooLogoNode.assertExists()
        headerNode.assertExists()
        mainTextNode.assertExists()
        serverInputNode.assertExists()
        emailInputNode.assertExists()
        passwordInputNode.assertExists()
        loginButtonNode.assertExists()
        loginWithHseButtonNode.assertExists()

        // checking text input, clearing and clear button vanishing
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
        serverInputNode.performTextInput(testInput)
        serverInputNode.assert(hasText(testInput))
        serverInputNode.onChild().assertExists()

        // checking text input, clearing and clear button vanishing
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

        // checking text input, clearing and clear button vanishing
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