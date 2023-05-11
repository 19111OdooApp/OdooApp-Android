package odoo.miem.android.common.uiKitComponents.cards

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarHalf
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import odoo.miem.android.common.uiKitComponents.R
import odoo.miem.android.common.uiKitComponents.text.HeadlineText
import odoo.miem.android.core.uiKitTheme.halfMainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.starColor
import kotlin.math.ceil
import kotlin.math.floor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedEmployeeCard(
    employeeName: String = stringResource(id = R.string.default_user_name),
    rating: Double,
    modifier: Modifier = Modifier,
    onActionClick: () -> Unit = {},
    starsCount: Int = 3,
    scheduleIconColor: Color = Color.Black,
    @DrawableRes statusIconRes: Int,
    onClick: () -> Unit = {},
) = Card(
    onClick = onClick,
    shape = RoundedCornerShape(20.dp),
    modifier = modifier
        .fillMaxWidth()
        .height(90.dp),
    border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.onBackground),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
) {
    val horizontalEdgePadding = 16.dp

    Row(
        modifier = modifier
            .padding(horizontal = horizontalEdgePadding)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            HeadlineText(
                text = employeeName,
            )

            Row {
                RatingBar(
                    rating = rating,
                    starsCount = starsCount,
                    starsColor = starColor
                )

                Spacer(modifier = Modifier.width(halfMainHorizontalPadding))

                Icon(
                    imageVector = Icons.Outlined.Schedule,
                    contentDescription = null,
                    tint = scheduleIconColor
                )
            }
        }

        IconButton(onClick = onActionClick) {
            Icon(
                painter = painterResource(statusIconRes),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    starsCount: Int = 5,
    starsColor: Color = Color.Yellow
) {
    val filledStars = floor(rating).toInt()
    val unfilledStars = (starsCount - ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))

    Row(modifier = modifier) {
        repeat(filledStars) {
            Icon(imageVector = Icons.Outlined.Star, contentDescription = null, tint = starsColor)
        }

        if (halfStar) {
            Icon(
                imageVector = Icons.Outlined.StarHalf,
                contentDescription = null,
                tint = starsColor
            )
        }

        repeat(unfilledStars) {
            Icon(
                imageVector = Icons.Outlined.StarOutline,
                contentDescription = null,
                tint = Color.Black
            )
        }
    }
}

@Preview
@Composable
fun OutlinedEmployeeCardPreview() {
    OutlinedEmployeeCard(rating = 1.3, statusIconRes = R.drawable.add_plus)
}
