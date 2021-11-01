package com.conlage.smartshopping.view.components.main.list.added

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.conlage.smartshopping.model.data.local.db.entity.Product

@ExperimentalMaterialApi
@Composable
fun AddedListProduct(
    productList: List<Product>,
    onProductClick: (Int) -> Unit,
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier
            .wrapContentSize()
    ) {
        itemsIndexed(productList) { i, product ->
            AddedProduct(
                product = product,
                onProductClick = { onProductClick(i) },
                productIndex = i,
                listSize = productList.size
            )
        }

    }

}