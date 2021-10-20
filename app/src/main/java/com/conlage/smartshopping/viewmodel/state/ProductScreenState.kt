package com.conlage.smartshopping.viewmodel.state

import com.conlage.smartshopping.model.data.local.ProductDetails

data class ProductScreenState(
    val product: ProductDetails? = null,
    val isLoading: Boolean = true,
    val isReadMore: Boolean = false,
    val isAdded: Boolean = false
)