package com.conlage.smartshopping.model.data.network.dto

data class NetworkProductList(
    val response: List<ProductItem>
) {
    data class ProductItem(
        val id: Int,
        val image: String,
        val price: String,
        val rate: Int,
        val title: String
    )
}