package com.conlage.smartshopping.viewmodel.state

import com.conlage.smartshopping.model.data.local.Product

data class MainScreenState(

    var searchList: MutableList<Product> = mutableListOf(),

    var bulbList: MutableList<Product> = mutableListOf(),

    var searchQuery: String = "",


    var isSearchOpen: Boolean = false,

    var isSearchError: Boolean = false,

    var isLoadingProducts: Boolean = false,

    var isLoadingSearchProducts: Boolean = false,

    var isCameraGranted: Boolean = true,

    var isStorageGranted: Boolean = true,

    var currentPage: Int = 1,

    var addItemTitle: String = "",

    var bulbTitle: String = "",

    var isLoadingBulb: Boolean = false,

    var isBulbOpen: Boolean = false,


    )
