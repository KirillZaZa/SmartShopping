package com.conlage.smartshopping.viewmodel.state

import com.conlage.smartshopping.model.data.local.db.entity.Product

data class MainScreenState(
    val productList: MutableList<Product> = mutableListOf(),
    val searchQuery: String? = null,
    val isSearchOpen: Boolean = false,
    val isLoading: Boolean = false
)
