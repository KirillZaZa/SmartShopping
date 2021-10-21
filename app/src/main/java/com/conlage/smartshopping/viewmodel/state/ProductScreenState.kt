package com.conlage.smartshopping.viewmodel.state

import com.conlage.smartshopping.model.data.local.ProductDetails

data class ProductScreenState(
    var product: ProductDetails? = null,
    val isLoading: Boolean = true,
    val isReadMore: Boolean = false,
    val isAdded: Boolean = false,
    val isEvaluation: Boolean = false
)