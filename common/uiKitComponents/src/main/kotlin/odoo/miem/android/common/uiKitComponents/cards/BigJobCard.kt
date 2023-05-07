package odoo.miem.android.common.uiKitComponents.cards

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import odoo.miem.android.common.uiKitComponents.R
import odoo.miem.android.common.uiKitComponents.utils.conditional
import odoo.miem.android.common.uiKitComponents.utils.drawDiagonalLabel
import odoo.miem.android.common.uiKitComponents.utils.getBackgroundColorCard
import odoo.miem.android.common.uiKitComponents.utils.glowEffect

/**
 * [BigModuleCard] is implementation of big module's card
 *
 * @param moduleName - name of module
 * @param numberOfNotification - number of module notification
 * @param isLiked - is module liked. If true, icon will be filled
 * @param onLikeClick - action, when user click on icon like
 * @param onClick - action, when user click on card
 *
 * @author Vorozhtsov Mikhail
 */
@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun BigJobCard(
    jobName: String,
    numberOfNewApplication: Int = 0,
    numberOfApplication: Int = 0,
    numberToRecruit: Int = 0,
    isLiked: Boolean = false,
    isPublished: Boolean = false,
    biggestNumber: Int = 1000,
    modifier: Modifier = Modifier,
    onLikeClick: () -> Unit = {},
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {}
) = Card(
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(
        containerColor = jobName.getBackgroundColorCard(),
    ),
    modifier = modifier
        .fillMaxWidth()
        .height(210.dp)
        .conditional(isPublished) {
            drawDiagonalLabel(
                text = "Published",
                color = Color.White
            )
        }
        .combinedClickable(
            onClick = onClick,
            onLongClick = onLongClick
        )
) {
    val horizontalPadding = 36.dp
    val topPadding = 12.dp

    Spacer(modifier = Modifier.height(topPadding))

    IconButton(
        onClick = onLikeClick,
        modifier = Modifier
            .size(45.dp)
            .align(Alignment.End)
            .padding(end = horizontalPadding / 2)
    ) {
        Icon(
            imageVector = if (isLiked) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.glowEffect(isLiked)
        )
    }

    Text(
        text = jobName,
        style = MaterialTheme.typography.titleLarge,
        color = Color.White,
        modifier = Modifier
            .align(Alignment.Start)
            .padding(start = horizontalPadding)
    )

    Spacer(modifier = Modifier.height(topPadding))

    Text(
        text = pluralStringResource(
            id = R.plurals.job_number_of_new_application,
            count = numberOfNewApplication
        ).format(numberOfNewApplication),
        style = MaterialTheme.typography.titleSmall.copy(
            textDecoration = TextDecoration.Underline
        ),
        color = Color.White,
        modifier = Modifier
            .align(Alignment.Start)
            .padding(start = horizontalPadding)
    )

    Spacer(modifier = Modifier.height(topPadding * 2))

    Row(
        modifier = Modifier
            .padding(horizontal = horizontalPadding)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MetaText(
            text = if (numberOfApplication > biggestNumber) {
                stringResource(id = R.string.job_big_number_of_application)
            } else {
                pluralStringResource(
                    id = R.plurals.job_number_of_application,
                    count = numberOfNewApplication
                ).format(numberOfApplication)
            }
        )
        MetaText(
            text = if (numberOfApplication > biggestNumber) {
                stringResource(id = R.string.job_big_number_to_recruit)
            } else {
                stringResource(
                    id = R.string.job_number_to_recruit
                ).format(numberToRecruit)
            }
        )
    }
}

@Composable
private fun MetaText(
    text: String
) = Text(
    text = text,
    style = MaterialTheme.typography.bodyMedium.copy(
        fontSize = 14.sp
    ),
    color = Color.White,
    maxLines = 1
)
