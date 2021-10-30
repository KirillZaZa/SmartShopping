package com.conlage.smartshopping.view.components.main.list

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.conlage.smartshopping.R
import com.conlage.smartshopping.model.data.local.db.entity.Product
import com.conlage.smartshopping.ui.theme.DarkGray
import com.conlage.smartshopping.ui.theme.ProductGray
import com.conlage.smartshopping.ui.theme.Yellow

@Composable
fun ProductDescription(product: Product) {


    Row(verticalAlignment = Alignment.CenterVertically) {

        ProductImage(bitmap = product.bitmap)

        //Description
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(2.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {

            //name
            Text(
                text = product.title,
                maxLines = 2,
                modifier = Modifier.width(124.dp),
                color = DarkGray,
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp
            )


            //rate
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 2.dp),
            ) {


                Image(
                    painter = painterResource(id = R.drawable.ic_star_icon),
                    contentDescription = "star_rate", modifier =
                    Modifier.size(20.dp)
                )

                Text(
                    text = "${product.rate}",
                    fontSize = 14.sp,
                    color = Yellow,
                    fontWeight = FontWeight.Light
                )

            }

            //price
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 2.dp),
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_price_icon),
                    contentDescription = "ruble_price", modifier =
                    Modifier.size(20.dp)
                )

                Text(
                    text = product.price,
                    fontSize = 12.sp,
                    color = DarkGray,
                    fontWeight = FontWeight.Normal,
                    maxLines = 2,
                )
            }
        }

    }

}

@Composable
fun ProductImage(bitmap: Bitmap?) {

    if (bitmap == null) {
        Image(
            painter = painterResource(id = R.drawable.ic_product_standin_icon),
            contentDescription = null,
            modifier = Modifier
                .height(88.dp)
                .width(88.dp)
                .padding(vertical = 4.dp)
                .padding(horizontal = 4.dp)
                .clip(RoundedCornerShape(20))
                .background(ProductGray),
        )
    } else {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .height(96.dp)
                .width(96.dp)
                .padding(vertical = 4.dp)
                .padding(horizontal = 4.dp)
                .clip(RoundedCornerShape(20)),
            contentScale = ContentScale.FillBounds
        )
    }

}