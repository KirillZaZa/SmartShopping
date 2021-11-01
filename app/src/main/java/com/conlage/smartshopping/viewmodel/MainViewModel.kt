package com.conlage.smartshopping.viewmodel

import com.conlage.smartshopping.model.data.local.db.entity.Product
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionState

interface MainViewModel {

    fun handleIncAddedProduct(product: Product)

    fun handleDecAddedProduct(product: Product)

    fun getProductList(): List<Product>

    fun handleIncSearchItem(productIndex: Int, callback:(product: Product) -> Unit)

    fun handleProductCheckBox(productIndex: Int)

    fun handleSearchQuery(query: String)

    fun handleSearchOpen(isOpen: Boolean)

    fun handleDecSearchItem(productIndex: Int, callback:(product: Product) -> Unit)

    fun handleCameraPermission(isGranted: Boolean)

    fun handleStoragePermission(isGranted: Boolean)

    fun handleNewPage()

    fun handleSaveProduct(productId: Int)

    fun handleDeleteProductById(productId: Int)

    suspend fun getProductListFromNetwork(query: String): List<Product>

}