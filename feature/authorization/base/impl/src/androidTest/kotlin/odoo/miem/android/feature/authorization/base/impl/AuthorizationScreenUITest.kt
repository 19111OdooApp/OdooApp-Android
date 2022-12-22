package odoo.miem.android.feature.authorization.base.impl

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
import androidx.test.platform.app.InstrumentationRegistry
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.core.uiKitTheme.commonPadding
import org.junit.Rule
import org.junit.Test

/**
 * [AuthorizationScreenUITest] - UI tests for [AuthorizationScreen]
 *
 * @author Egor Danilov
 */
class AuthorizationScreenUITest {
    private val context by lazy { InstrumentationRegistry.getInstrumentation().context }

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testUiAndTextFields() {
        val headerDesc = context.getString(R.string.login_welcome_header)
        val mainTextDesc = context.getString(R.string.login_welcome_text)
        val serverTextFieldDesc = context.getString(R.string.login_odoo_server)
        val emailTextFieldDesc = context.getString(R.string.login_email)
        val passwordTextFieldDesc = context.getString(R.string.login_password)
        val loginButtonDesc = context.getString(R.string.login_button_desc)
        val loginWithHseButtonDesc = context.getString(R.string.login_with_hse_button_desc)
        val trailingIconDesc = context.getString(odoo.miem.android.common.uiKitComponents.R.string.text_field_trailing_icon_desc)
        val testInput = context.getString(R.string.test_input)

        // Preparation
        composeTestRule.setContent {
            val navController = rememberNavController()
            OdooMiemAndroidTheme {
                AuthorizationScreen().AuthorizationScreen(navController) {}
            }
        }
        val headerNode = composeTestRule.onNodeWithText(headerDesc)
        val mainTextNode = composeTestRule.onNodeWithText(mainTextDesc)
        val serverInputNode = composeTestRule.onNodeWithText(serverTextFieldDesc)
        val emailInputNode = composeTestRule.onNodeWithText(emailTextFieldDesc)
        val passwordInputNode = composeTestRule.onNodeWithText(passwordTextFieldDesc)
        val loginButtonNode = composeTestRule.onNodeWithContentDescription(loginButtonDesc)
        val loginWithHseButtonNode = composeTestRule.onNodeWithContentDescription(loginWithHseButtonDesc)

        // Asserting

        // checking if all ui elements are displayed
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

        // trying to toggle visibility
        passwordInputNode
            .onChild()
            .assertExists()
            .performClick()

        passwordInputNode.assert(hasText(testInput))
    }
}