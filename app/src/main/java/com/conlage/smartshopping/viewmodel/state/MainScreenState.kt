package com.conlage.smartshopping.viewmodel.state

import com.conlage.smartshopping.model.data.local.db.entity.Product

data class MainScreenState(
    val productList: MutableList<Product> = mutableListOf(),
    val searchList: MutableList<Product> = mutableListOf(),
    var searchQuery: String? = null,
    var isSearchOpen: Boolean = false,
    var isLoadingProducts: Boolean = false,
    var isLoadingSearchProducts: Boolean = false,
    var currentPage: Int = 1
)
