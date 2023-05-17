package odoo.miem.android.feature.employees.impl.employeesScreen.allEmployees.components

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.rounded.QuestionMark
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import odoo.miem.android.common.network.employees.api.entities.EmployeeBasicInfo
import odoo.miem.android.common.uiKitComponents.text.HeadlineText
import odoo.miem.android.common.uiKitComponents.text.LabelText
import odoo.miem.android.core.uiKitTheme.commonPadding
import odoo.miem.android.core.uiKitTheme.odooGray

/**
 * [EmployeeCard] is implementation of small card for employees
 *
 * @param employee is current employee DTO
 *
 * @author Egor Danilov
 */
@Composable
fun EmployeeCard(
    employee: EmployeeBasicInfo,
    modifier: Modifier = Modifier,
    onClick: (employee: EmployeeBasicInfo) -> Unit = {}
) = Card(
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.background
    ),
    border = BorderStroke(
        width = 1.dp,
        color = MaterialTheme.colorScheme.onPrimary
    ),
    modifier = modifier
        .clip(RoundedCornerShape(20.dp))
        .fillMaxWidth()
        .clickable { onClick(employee) }
) {
    val imageBytes = Base64.decode(employee.avatar, Base64.DEFAULT)
    val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxSize()
            .padding(commonPadding)
    ) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .size(74.dp)
            )
        } else {
            Icon(
                imageVector = Icons.Rounded.QuestionMark,
                contentDescription = null,
                tint = odooGray,
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .size(74.dp)
            )
        }

        Spacer(modifier = Modifier.width(commonPadding))

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            HeadlineText(
                text = employee.name.uppercase(),
                isMedium = false
            )

            Spacer(modifier = Modifier.height(6.dp))

            employee.job?.let {
                LabelText(
                    text = it,
                    isMedium = false
                )
            }

            Spacer(modifier = Modifier.height(commonPadding))

            employee.email?.let {
                LabelText(
                    text = it,
                    isMedium = false
                )

                Spacer(modifier = Modifier.height(commonPadding))
            }

            employee.phone?.let {
                LabelText(
                    text = it,
                    isMedium = false
                )
            }
        }
    }
}