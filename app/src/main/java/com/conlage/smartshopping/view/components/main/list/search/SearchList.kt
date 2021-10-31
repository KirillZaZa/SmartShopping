package com.conlage.smartshopping.view.components.main.list.search

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.conlage.smartshopping.model.data.local.db.entity.Product
import com.conlage.smartshopping.ui.theme.LightGray
import com.conlage.smartshopping.viewmodel.impl.MainViewModelImpl

@ExperimentalMaterialApi
@Composable
fun SearchList(
    searchList: List<Product>,
    onProductClick: (Int) -> Unit,
    incClick: (Int) -> Unit,
    decClick: (Int) -> Unit
) {

    val listState = rememberLazyListState()

    Spacer(modifier = Modifier.height(8.dp))


    LazyColumn(
        state = listState,
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .wrapContentSize()
    ) {
        itemsIndexed(searchList) { i,product ->
            SearchItem(
                product = product,
                onProductClick = { onProductClick(i) },
                incClick = {incClick(i)},
                decClick = {decClick(i)}
            )
        }

    }

    Spacer(modifier = Modifier.height(24.dp))





}