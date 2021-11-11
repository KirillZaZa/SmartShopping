package com.conlage.smartshopping.model.data.network.dto

import com.google.gson.annotations.SerializedName

data class NetworkProduct(
    val response: Response
) {
    data class Response(
        val advantages: List<String>,
        val barcode: String?,
        val description: String,
        val details: Map<String, String>,
        val disadvantages: List<String>,
        val id: Int,
        val image: String,
        val price: String,
        val rate: Double,
        val rate_details: Map<String, Double>,
        val research_document: String,
        val title: String
    )
}