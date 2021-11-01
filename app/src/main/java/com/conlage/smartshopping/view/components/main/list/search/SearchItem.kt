package com.conlage.smartshopping.view.components.main.list.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.conlage.smartshopping.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.conlage.smartshopping.model.data.local.db.entity.Product
import com.conlage.smartshopping.ui.theme.*
import com.conlage.smartshopping.view.components.main.list.ProductDescription

/**
 * @param product: Product
 *
 */
@ExperimentalMaterialApi
@Composable
fun SearchItem(
    product: Product,
    onProductClick: () -> Unit,
    incClick: () -> Unit,
    decClick: () -> Unit,
    isLast: Boolean,
) {

    Card(
        onClick = onProductClick,
        backgroundColor = LightBlue,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(20),
        elevation = 0.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ProductDescription(product)

            Spacer(modifier = Modifier.weight(1f))
            if (product.quantity != 0) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DecProductButton(decClick)
                    Text(
                        text = "${product.quantity}",
                        fontSize = 14.sp,
                        color = DarkGray,
                        fontWeight = FontWeight.Normal
                    )
                    IncProductButton(incClick)
                }

            } else {
                IncProductButton(incClick)
            }
        }
    }

    if (isLast) {
        Column(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(24.dp))
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Нажмите на товар, чтобы увидеть подробности",
                color = LightGray,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.weight(1f))

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}


@Composable
private fun IncProductButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick, modifier = Modifier
            .padding(end = 8.dp)
            .size(32.dp)
            .background(color = Blue, shape = RoundedCornerShape(20))
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_add_24),
            contentDescription = "inc_product",
            modifier = Modifier.size(16.dp),
            tint = Color.White
        )
    }
}

@Composable
fun DecProductButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick, modifier = Modifier
            .padding(start = 16.dp)
            .size(32.dp)
            .background(color = ProductGray, shape = RoundedCornerShape(20))
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_minus_icon),
            contentDescription = "dec_product",
            modifier = Modifier.size(12.dp),
            tint = DarkGray
        )
    }
}






