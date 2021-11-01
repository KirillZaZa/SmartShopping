package com.conlage.smartshopping.view.components.product.about

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp

@Composable
fun AboutProduct(
    bitmap: Bitmap,
    rate: Double,
    price: String,
    onAboutRateClick: () -> Unit,
    onResearchingClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "image_product",
            modifier = Modifier.size(200.dp)
        )

        Column(
            modifier = Modifier.padding(start = 16.dp), horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

        }

    }

}

@Composable
private fun RateProduct(onClick: () -> Unit) {

}

@Composable
private fun PriceProduct() {

}

@Composable
private fun ResearchProduct(onClick: () -> Unit){
    Row(verticalAlignment = Alignment.CenterVertically) {
    }
}

