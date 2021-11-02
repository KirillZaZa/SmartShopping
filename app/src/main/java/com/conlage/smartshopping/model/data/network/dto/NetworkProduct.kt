package com.conlage.smartshopping.model.data.network.dto

data class NetworkProduct(
    val response: Response
) {
    data class Response(
        val advantages: List<String>,
        val barcode: String,
        val description: String,
        val details: Details,
        val disadvantages: List<String>,
        val id: Int,
        val image: String,
        val price: String,
        val rate: Double,
        val rate_details: RateDetails,
        val research_document: String,
        val title: String
    ) {
        data class Details(
            val weight: String,
            val composition: String,
            val barcode: String,
            val manufacturer: String,
            val producer: String,
            val yearOfResearch: String,
            val countryOfManufacture: String
        )

        data class RateDetails(
            val rate_details: Map<String, Int>
        )
    }
}