package com.conlage.smartshopping.viewmodel

import androidx.compose.foundation.lazy.LazyListState
import com.conlage.smartshopping.model.data.local.Product
import com.conlage.smartshopping.model.data.local.db.entity.ShopItem

interface MainViewModel {



    fun handleScannerButton()

    fun handleShopItemQuery(title: String)

    fun handleSnackbarPermission()

    fun handleNewShopItem()

    fun getProductList()

    fun handleProductCheckBox(shopItemIndex: Int, wantBeDeleted: Boolean)

    fun handleDialogState(shopItemTitle: String, state: Boolean)

    fun handleSearchQuery(query: String)

    fun handleSearchOpen(isOpen: Boolean)

    fun handlePermission(denied: Boolean)

    fun handleNewPage()

    fun handleBulbCheckbox(isChecked: Boolean)

    fun handleListStates(vararg state: Pair<Int,Int>?)


    suspend fun getProductListFromNetwork(query: String): List<Product>

}