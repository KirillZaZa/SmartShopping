package com.conlage.smartshopping.viewmodel

import com.conlage.smartshopping.model.data.local.db.entity.Product

interface MainViewModel {

    fun getProductList(): List<Product>

    fun handlePlusButton(newProduct: Product)

    fun handleIncProduct(productIndex: Int)

    fun handleProductCheckBox(productIndex: Int)

    fun handleSearchQuery(query: String)

    fun handleSearchOpen(isOpen: Boolean)

    fun handleDeleteProduct(product: Product)

    fun handleDecProduct(productIndex: Int)

    fun handleNewPage()

    fun handleSaveProduct(productId: Int)

    suspend fun getProductListFromNetwork(query: String): List<Product>

}