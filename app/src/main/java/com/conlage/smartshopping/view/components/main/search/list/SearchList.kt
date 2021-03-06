package com.conlage.smartshopping.view.components.main.search.list

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.conlage.smartshopping.model.data.local.Product

@ExperimentalMaterialApi
@Composable
fun SearchList(
    searchList: List<Product>?,
    onProductClick: (Int) -> Unit,
    searchListState: LazyListState
) {


    Spacer(modifier = Modifier.height(4.dp))

    if (searchList.isNullOrEmpty()) return


    LazyColumn(
        state = searchListState,
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .wrapContentSize()
    ) {
        Log.e("SearchList", "error here: ${searchList.size} ", )

        itemsIndexed(searchList) {i, product ->

            val isLast = i == searchList.size - 1
            SearchItem(product = product, onProductClick = { onProductClick(i) }, isLast = isLast)

        }

    }

    Spacer(modifier = Modifier.height(24.dp))


}