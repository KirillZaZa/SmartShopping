package com.conlage.smartshopping.viewmodel.state

import com.conlage.smartshopping.model.data.local.ProductDetails
import com.conlage.smartshopping.model.data.local.Product

data class ProductScreenState(
    var productDetails: ProductDetails? = null,
    val product: Product? = null,
    val isLoading: Boolean = true,
    val isReadMore: Boolean = false,
    val isAdded: Boolean = false,
    val isEvaluation: Boolean = false,
    val isClosing: Boolean = false,
)