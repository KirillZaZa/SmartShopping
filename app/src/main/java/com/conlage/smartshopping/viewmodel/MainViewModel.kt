package com.conlage.smartshopping.viewmodel

import com.conlage.smartshopping.model.data.local.Product
import com.conlage.smartshopping.model.data.local.db.entity.ShopItem

interface MainViewModel {


    fun handleShopItemQuery(title: String)

    fun handleNewShopItem()

    fun getProductList()

    fun handleProductCheckBox(shopItemIndex: Int, wantBeDeleted: Boolean)

    fun handleDialogState(shopItemTitle: String, state: Boolean)

    fun handleSearchQuery(query: String)

    fun handleSearchOpen(isOpen: Boolean)

    fun handleCameraPermission(isGranted: Boolean)

    fun handleNewPage()


    suspend fun getProductListFromNetwork(query: String): List<Product>

}