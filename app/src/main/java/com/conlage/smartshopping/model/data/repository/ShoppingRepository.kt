package com.conlage.smartshopping.model.data.repository

import com.conlage.smartshopping.model.data.network.wrapper.ShoppingResponse

interface ShoppingRepository {


    fun getProductList(page: Int, callback: (ShoppingResponse) -> Unit)

    fun getProductById(callback: (ShoppingResponse) -> Unit)

    fun getProductByBarcodeFromNetwork(callback: (ShoppingResponse) -> Unit)

    fun getProductByBarcodeFromDb(callback: (ShoppingResponse) -> Unit)

}