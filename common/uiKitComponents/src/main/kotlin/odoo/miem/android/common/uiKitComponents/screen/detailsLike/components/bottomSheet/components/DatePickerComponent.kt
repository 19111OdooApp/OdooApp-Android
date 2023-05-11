package odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.bottomSheet.components

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import odoo.miem.android.common.uiKitComponents.text.SubtitleText
import odoo.miem.android.common.uiKitComponents.textfields.BaseTextField
import java.util.*

@Suppress("ImplicitDefaultLocale")
@Composable
internal fun DatePickerComponent(
    placeholderText: String,
    onDone: (result: String) -> Unit = {}
) {
    var input by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()
    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, year: Int, month: Int, day: Int ->
            input = TextFieldValue(String.format("%02d/%02d/%4d", day, month, year))
            onDone(input.text)
        },
        year,
        month,
        day
    )

    BaseTextField(
        value = input,
        onValueChange = {
            input = it
        },
        placeholder = {
            SubtitleText(
                text = placeholderText,
                color = Color.Gray
            )
        },
        enabled = false,
        label = null,
        textStyle = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Start),
        modifier = Modifier
            .clickable {
                datePickerDialog.show()
            }
            .height(60.dp)
    )
}
