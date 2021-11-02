package com.conlage.smartshopping.view.components.productitem.advantages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.conlage.smartshopping.R
import com.conlage.smartshopping.ui.theme.GreenColor
import com.conlage.smartshopping.ui.theme.RedColor

@Composable
fun AdvantageItem(
    item: String,
    isAdvantage: Boolean
) {

    val image = if (isAdvantage) R.drawable.ic_advantage_icon
    else R.drawable.ic_disadvantage_icon

    val textColor = if (isAdvantage) GreenColor
    else RedColor


    Row(verticalAlignment = Alignment.Top) {

        Image(painter = painterResource(id = image), contentDescription = "advantage_image", modifier = Modifier.offset(y = 4.dp))

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = item,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.sizeIn(maxWidth = 112.dp).padding(vertical = 2.dp)
        )
    }

}