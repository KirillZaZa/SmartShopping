package com.conlage.smartshopping.viewmodel

import com.conlage.smartshopping.model.data.local.ProductDetails

interface ProductViewModel {

    fun getProductDetails(productId: Int?, isAdded:Boolean, barcode: String?)

    fun handleAddButton()

    fun handleDeleteButton()

    fun handleReadMore()

    fun handleAboutEvaluation()

    suspend fun getProductDetailsById(productId: Int): ProductDetails?

    suspend fun getProductDetailsByBarcode(barcode: String?): ProductDetails?
}
