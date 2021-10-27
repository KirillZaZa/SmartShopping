package com.conlage.smartshopping.viewmodel

import com.conlage.smartshopping.model.data.local.ProductDetails

interface ProductViewModel {

    fun getProductDetails(): ProductDetails?



    fun handleAddButton()

    fun handleDeleteButton()

    fun handleReadMore()

    fun handleAboutEvaluation()

    suspend fun getProductDetailsById(): ProductDetails?

    suspend fun getProductDetailsByBarcode(): ProductDetails?
}
