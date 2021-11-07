package com.conlage.smartshopping.view.components.productitem.about

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.conlage.smartshopping.R
import com.conlage.smartshopping.ui.theme.Blue
import com.conlage.smartshopping.ui.theme.DarkGray
import com.conlage.smartshopping.ui.theme.Yellow

@Composable
fun AboutProduct(
    bitmap: Bitmap?,
    rate: Double,
    price: String?,
    onAboutRateClick: () -> Unit,
    onResearchingClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        if(bitmap == null){
            Image(
                painter = painterResource(id = R.drawable.ic_product_standin_icon),
                contentDescription = "image_product",
                modifier = Modifier
                    .size(126.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
        }else {
            Image(
                bitmap = bitmap!!.asImageBitmap(),
                contentDescription = "image_product",
                modifier = Modifier
                    .size(126.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
        }
 

        Column(
            modifier = Modifier.padding(start = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            RateProduct(rate = "$rate", onClick = onAboutRateClick)

            PriceProduct(price = price)

            ResearchProduct(onClick = onResearchingClick)

        }

    }


}

@Composable
private fun RateProduct(rate: String, onClick: () -> Unit) {

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(onClick = onClick),
    ) {


        Image(
            painter = painterResource(id = R.drawable.ic_star_icon),
            contentDescription = "star_rate",
            modifier = Modifier.size(20.dp)
        )

        Text(
            text = rate,
            fontSize = 16.sp,
            color = Yellow,
            fontWeight = FontWeight.Medium
        )


        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_24),
            contentDescription = "star_rate",
            modifier = Modifier
                .size(24.dp)
                .rotate(180f)
                .offset(x = (8).dp),
            tint = Yellow
        )


    }


}

@Composable
private fun PriceProduct(price: String?) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_price_icon),
            contentDescription = "price_icon",
            modifier = Modifier.size(20.dp)
        )

        Text(
            text = price ?: "нет цены",
            fontSize = 16.sp,
            color = DarkGray,
            fontWeight = FontWeight.W400
        )

    }
}

@Composable
private fun ResearchProduct(onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.clickable(onClick = onClick)
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_doc_icon),
            contentDescription = "price_icon",
            modifier = Modifier.size(20.dp)
        )

        Text(
            text = "Исследование",
            color = Blue,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_24),
            contentDescription = "star_rate",
            modifier = Modifier
                .size(16.dp)
                .rotate(180f)
                .offset(x = (8).dp),
            tint = Blue
        )

    }
}

