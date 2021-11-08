package com.conlage.smartshopping.view.components.main.search.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.conlage.smartshopping.model.data.local.Product
import com.conlage.smartshopping.ui.theme.*
import com.conlage.smartshopping.view.components.main.added_list.ProductDescription

/**
 * @param product: Product
 *
 */
@ExperimentalMaterialApi
@Composable
fun SearchItem(
    product: Product,
    onProductClick: () -> Unit,
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
        ProductDescription(product)
    }

    if (isLast) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(24.dp))
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Нажмите на товар, чтобы увидеть подробности",
                color = LightGray,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(1f))

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}







