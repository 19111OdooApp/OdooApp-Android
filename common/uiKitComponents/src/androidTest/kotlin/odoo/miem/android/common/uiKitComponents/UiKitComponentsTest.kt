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
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.text.input.TextFieldValue
import odoo.miem.android.common.uiKitComponents.textfields.LoginTextField
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

/**
 * [UiKitComponentsTest] - UI tests for UI components from odoo.miem.android.common.uiKitComponents package
 *
 * Contains:
 * [loginTextField_test] - test for [LoginTextField]
 *
 * @author Egor Danilov
 */
class UiKitComponentsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginTextField_test() {
        lateinit var textFieldDesc: String
        lateinit var trailingIconDesc: String
        lateinit var testInput: String

        composeTestRule.setContent {
            var textFieldInput by rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(TextFieldValue())
            }

            textFieldDesc = stringResource(R.string.test_text_field_desc)
            trailingIconDesc = stringResource(R.string.text_field_trailing_icon_desc)
            testInput = stringResource(R.string.test_input)

            OdooMiemAndroidTheme {
                LoginTextField(
                    value = textFieldInput,
                    onValueChange = { textFieldInput = it },
                    modifier = Modifier.semantics {
                        contentDescription = textFieldDesc
                    }
                )
            }
        }
        val textFieldNode = composeTestRule.onNodeWithContentDescription(textFieldDesc)
        val trailingIconNode = composeTestRule.onNodeWithContentDescription(trailingIconDesc)

        textFieldNode.assertExists()
        trailingIconNode.assertDoesNotExist()

        textFieldNode.assert(hasText(""))

        textFieldNode.performTextInput(testInput)
        textFieldNode.assert(hasText(testInput))

        trailingIconNode.assertExists()
        trailingIconNode.performClick()

        trailingIconNode.assertDoesNotExist()
        textFieldNode.assert(hasText(""))
    }
}