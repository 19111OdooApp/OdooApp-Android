package odoo.miem.android.common.uiKitComponents

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.text.input.TextFieldValue
import androidx.test.platform.app.InstrumentationRegistry
import odoo.miem.android.common.uiKitComponents.textfields.LoginTextField
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import org.junit.Rule
import org.junit.Test

/**
 * [UiKitComponentsTest] - UI tests for UI components from odoo.miem.android.common.uiKitComponents package
 *
 * Contains:
 * [] - test for [LoginTextField]
 *
 * @author Egor Danilov
 */
class UiKitComponentsTest {
    private val context by lazy { InstrumentationRegistry.getInstrumentation().context }

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testInputAndClearLoginTextField() {
        val textFieldLabel = context.getString(R.string.test_text_field_desc)
        val trailingIconDesc = context.getString(R.string.text_field_trailing_icon_desc)
        val testInput = context.getString(R.string.test_input)

        // Preparation
        composeTestRule.setContent {
            var textFieldInput by rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(TextFieldValue())
            }

            OdooMiemAndroidTheme {
                LoginTextField(
                    value = textFieldInput,
                    labelResource = R.string.test_text_field_desc,
                    onValueChange = { textFieldInput = it },
                )
            }
        }
        val textFieldNode = composeTestRule.onNodeWithText(textFieldLabel)
        val trailingIconNode = composeTestRule.onNodeWithContentDescription(trailingIconDesc)

        // Asserting

        // Checking if all ui elements are displayed
        textFieldNode.assertExists()
        trailingIconNode.assertDoesNotExist()
        textFieldNode.assert(hasText(""))

        textFieldNode.performTextInput(testInput)
        textFieldNode.assert(hasText(testInput))

        // Cleaning
        trailingIconNode.assertExists()
        trailingIconNode.performClick()
        trailingIconNode.assertDoesNotExist()
        textFieldNode.assert(hasText(""))
    }
}