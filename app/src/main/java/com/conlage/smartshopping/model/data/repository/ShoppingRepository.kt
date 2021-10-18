package com.conlage.smartshopping.model.data.repository

import com.conlage.smartshopping.model.data.local.db.entity.Product
import com.conlage.smartshopping.model.data.network.resultwrapper.ShoppingResponse

interface ShoppingRepository {


    fun getProductList(query: String, page: Int, callback: (ShoppingResponse) -> Unit)

    fun getProductById(id: Int, callback: (ShoppingResponse) -> Unit)

    fun getProductByBarcode(barcode: String, callback: (ShoppingResponse) -> Unit)

    fun saveProductInDb(product: Product, callback: (ShoppingResponse) -> Unit)

    fun deleteProductFromDb(product: Product, callback: (ShoppingResponse) -> Unit)


}