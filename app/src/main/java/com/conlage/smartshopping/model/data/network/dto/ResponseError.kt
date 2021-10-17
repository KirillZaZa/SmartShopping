package com.conlage.smartshopping.model.data.network.dto

data class ResponseError(
    val error: Error
) {
    data class Error(
        val code: Int,
        val localizedMessage: String,
        val message: String
    )
}