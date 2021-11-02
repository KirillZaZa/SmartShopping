package com.conlage.smartshopping.view.components.main.list.added

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.conlage.smartshopping.model.data.local.db.entity.Product
import com.conlage.smartshopping.ui.theme.Blue
import com.conlage.smartshopping.ui.theme.LightBlue
import com.conlage.smartshopping.ui.theme.LightGray
import com.conlage.smartshopping.ui.theme.ProductGray
import com.conlage.smartshopping.view.components.main.list.ProductDescription

@ExperimentalMaterialApi
@Composable
fun AddedProduct(
    product: Product,
    onProductClick: () -> Unit,
    productIndex: Int,
    listSize: Int,
) {
    val modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)
    if (productIndex == 0) {
        Spacer(modifier = Modifier.height(48.dp))
    }


    Card(
        onClick = onProductClick,
        backgroundColor = LightBlue,
        modifier = modifier,
        shape = RoundedCornerShape(20),
        elevation = 0.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ProductDescription(product)

            Spacer(modifier = Modifier.weight(1f))
            //quantity
            Box(
                modifier = Modifier
                    .width(50.dp)
                    .height(30.dp)
                    .padding(end = 8.dp)
                    .background(color = ProductGray, shape = RoundedCornerShape(20)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${product.quantity}",
                    color = Blue,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                )
            }
        }
    }

    if (productIndex == listSize - 1) {
        Spacer(modifier = Modifier.height(96.dp))
    }

}