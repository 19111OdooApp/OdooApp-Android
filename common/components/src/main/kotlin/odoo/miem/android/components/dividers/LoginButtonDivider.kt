package odoo.miem.android.components.dividers

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginButtonDivider() = Row(
    modifier = Modifier.padding(vertical = 15.dp)
) {
    val rectangleWidth = 100.dp

    Surface(
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        modifier = Modifier
            .size(rectangleWidth, 1.dp)
            .align(Alignment.CenterVertically),
        content = {}
    )

    Text(
        text = "OR",
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        modifier = Modifier.padding(horizontal = 10.dp)
    )

    Surface(
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        modifier = Modifier
            .size(rectangleWidth, 1.dp)
            .align(Alignment.CenterVertically),
        content = {}
    )
}