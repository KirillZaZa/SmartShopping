package com.conlage.smartshopping.viewmodel

import com.conlage.smartshopping.model.data.local.db.entity.Product

interface MainViewModel {

    fun handlePlusButton()

    fun handleIncProduct(product: Product)

    fun handleOnProductClick(productItem: Int)

    fun handleSearchQuery(query: String)

    fun handleSearchOpen(isOpen: Boolean)

    fun handleScanBarcode(barcode: String)

    fun handleDeleteProduct(product: Product)

}