package odoo.miem.android.common.uiKitComponents.cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import odoo.miem.android.common.uiKitComponents.R
import odoo.miem.android.common.uiKitComponents.utils.animatedGradientBorder

/**
 * [BigModuleOutlinedCard] is implementation of big add module's card
 *
 * @param onClick - action, when user click on card
 * @param gradientColors - gradient colors of the border
 *
 * @author Egor Danilov
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BigModuleOutlinedCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    gradientColors: List<Color>,
) = Card(
    onClick = onClick,
    shape = RoundedCornerShape(20.dp),
    elevation = CardDefaults.cardElevation(
        defaultElevation = 4.dp
    ),
    border = BorderStroke(
        width = 4.dp,
        brush = animatedGradientBorder(
            colors = gradientColors,
            startX = 0f,
            startY = 0f,
            height = 1050f
        )
    ),
    modifier = modifier
        .fillMaxWidth()
        .height(210.dp)
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = stringResource(R.string.outlined_module_card_header),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Icon(
            painter = painterResource(R.drawable.ic_add),
            contentDescription = stringResource(R.string.add_favourite_module_button_icon),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(45.dp)
                .padding(top = 8.dp)
        )
    }
}
