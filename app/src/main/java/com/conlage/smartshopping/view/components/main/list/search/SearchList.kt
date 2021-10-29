package com.conlage.smartshopping.view.components.main.list.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.conlage.smartshopping.model.data.local.db.entity.Product
import com.conlage.smartshopping.viewmodel.impl.MainViewModelImpl

@ExperimentalMaterialApi
@Composable
fun SearchList(
   searchList: List<Product>,
   onProductClick: () -> Unit,
   incClick: () -> Unit,
   decClick: () -> Unit
) {

    val listState = rememberLazyListState()


    LazyColumn(
        state = listState,
        modifier = Modifier.wrapContentSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(searchList) { product ->
            SearchItem(
                product = product,
                onProductClick = onProductClick,
                incClick = incClick,
                decClick = decClick
            )
        }
    }


}