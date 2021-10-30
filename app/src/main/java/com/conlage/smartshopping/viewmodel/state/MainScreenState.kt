package com.conlage.smartshopping.viewmodel.state

import com.conlage.smartshopping.model.data.local.db.entity.Product
import com.conlage.smartshopping.view.components.main.floating_button.FabStateEnum

data class MainScreenState(
    val productList: MutableList<Product> = mutableListOf(),
    val searchList: MutableList<Product> = mutableListOf(),
    var searchQuery: String = "",
    var isSearchOpen: Boolean = false,
    var isSearchError: Boolean = false,
    var isLoadingProducts: Boolean = false,
    var fabState: FabStateEnum = FabStateEnum.COLLAPSED,
    var isLoadingSearchProducts: Boolean = false,
    var isCameraGranted: Boolean = true,
    var isStorageGranted: Boolean = true,
    var currentPage: Int = 1
)
