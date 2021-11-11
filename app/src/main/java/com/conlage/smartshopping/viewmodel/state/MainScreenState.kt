package com.conlage.smartshopping.viewmodel.state

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import com.conlage.smartshopping.model.data.local.Product
import com.conlage.smartshopping.model.data.local.db.entity.ShopItem

data class MainScreenState(

    var searchList: MutableList<Product> = mutableListOf(),

    var bulbList: MutableList<Product> = mutableListOf(),

    var searchQuery: String = "",

    var isScannerOpen: Boolean = false,

    var isSearchOpen: Boolean = false,

    var isSearchError: Boolean = false,

    var isShouldShowRationale: Boolean = false,

    var isLoadingProducts: Boolean = false,

    var isLoadingSearchProducts: Boolean = false,

    var currentPage: Int = 1,

    var addItemTitle: String = "",

    var bulbTitle: String = "",

    var isLoadingBulb: Boolean = false,

    var isBulbOpen: Boolean = false,

    var addedProductListState: Pair<Int, Int> = 0 to 0,

    var searchListState: Pair<Int, Int> = 0 to 0,

    var bulbListState: Pair<Int, Int> = 0 to 0,

    var currentBulbIndex: Int = 0



    )
