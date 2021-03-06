package com.conlage.smartshopping.view.components.main.added_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.conlage.smartshopping.R
import com.conlage.smartshopping.model.data.local.Product
import com.conlage.smartshopping.ui.theme.*
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.glide.LocalGlideRequestOptions

@Composable
fun ProductDescription(product: Product) {


    Row(verticalAlignment = Alignment.CenterVertically) {

        ProductImage(product.image)

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
                modifier = Modifier.width(224.dp),
                color = DarkGray,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis
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
                    Modifier.size(16.dp)
                )

                Text(
                    text = "${product.rate}",
                    fontSize = 12.sp,
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
                    Modifier.size(16.dp)
                )

                Text(
                    text = product.price,
                    fontSize = 12.sp,
                    color = DarkGray,
                    fontWeight = FontWeight.W400,
                    maxLines = 2,
                )
            }


        }

    }

}

@Composable
fun ProductImage(url: String) {


    val requestOptions = RequestOptions
        .overrideOf(200, 200)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerCrop()


    CompositionLocalProvider(LocalGlideRequestOptions provides requestOptions) {
        GlideImage(
            imageModel = url,
            modifier = Modifier
                .height(80.dp)
                .width(80.dp)
                .padding(vertical = 4.dp)
                .padding(horizontal = 4.dp)
                .clip(RoundedCornerShape(20))
                .background(Color.White),
            shimmerParams = ShimmerParams(
                baseColor = Blue,
                highlightColor = LightGray,
                durationMillis = 500,
                dropOff = 0.65f,
                tilt = 20f
            ),
            failure = {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
                    Icon(
                        painter = painterResource(id = R.drawable.ic_product_error_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .height(32.dp)
                            .width(32.dp),
                        tint = Blue
                    )
                }

            },
            circularReveal = CircularReveal(duration = 500)
        )
    }
    




}
