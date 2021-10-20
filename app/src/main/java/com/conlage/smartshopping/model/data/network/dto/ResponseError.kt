package com.conlage.smartshopping.model.data.network.dto

data class ResponseError(
    val error: Error
) {
    data class Error(
        val code: Int = 404,
        val localizedMessage: String = "Товар не найден",
        val message: String = "Product not found"
    )
}