package com.conlage.smartshopping.view.components.main.added_list.list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.conlage.smartshopping.model.data.local.db.entity.ShopItem

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun AddedListProduct(
    productList: List<ShopItem>,
    onLightClick: (ShopItem) -> Unit,
    onCheckedChange: (Int, Boolean) -> Unit,
    addedListState:LazyListState
) {



    LazyColumn(
        state = addedListState,
        modifier = Modifier
            .fillMaxSize()
            .offset(y = (-20).dp)
    ) {
        itemsIndexed(productList) { i, item ->
            AddedProduct(
                shopItem = item,
                onLightClick = {
                    onLightClick(item)
                },
                productIndex = i,
                listSize = productList.size,
                onCheckedChange = {
                    onCheckedChange(i, it)
                },
            )
        }

    }

}