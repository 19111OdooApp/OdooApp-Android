package odoo.miem.android.common.uiKitComponents.cards

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import odoo.miem.android.common.uiKitComponents.R
import odoo.miem.android.common.uiKitComponents.utils.getBackgroundColorCard
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun BigModuleCard(
    moduleName: String = stringResource(id = R.string.module_default_name),
    numberOfNotification: Int = 0,
    isLiked: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) = Card(
    onClick = onClick,
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(
        containerColor = moduleName.getBackgroundColorCard(),
    ),
    elevation = CardDefaults.cardElevation(
        defaultElevation = 4.dp
    ),
    modifier = modifier
        .fillMaxWidth()
        .height(210.dp)
) {
    val horizontalPadding = 36.dp
    val topPadding = 12.dp

    Spacer(modifier = Modifier.height(topPadding))
    // TODO Icon click handle
    // TODO Glow effect when like?
    Icon(
        imageVector = if (isLiked) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
        contentDescription = null,
        tint = Color.White,
        modifier = Modifier
            .size(45.dp)
            .align(Alignment.End)
            .padding(end = horizontalPadding / 2)
    )

    Text(
        text = moduleName,
        style = MaterialTheme.typography.titleLarge,
        color = Color.White,
        modifier = Modifier
            .align(Alignment.Start)
            .padding(start = horizontalPadding)
    )

    Spacer(modifier = Modifier.height(topPadding))

    Text(
        text = pluralStringResource(
            id = R.plurals.module_number_of_notifications,
            count = numberOfNotification
        ).format(numberOfNotification),
        style = MaterialTheme.typography.titleSmall.copy(
            textDecoration = TextDecoration.Underline
        ),
        color = Color.White,
        modifier = Modifier
            .align(Alignment.Start)
            .padding(start = horizontalPadding)
    )
}
