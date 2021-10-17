package com.conlage.smartshopping.model.data.network.dto

data class NetworkProduct(
    val response: Response
) {
    data class Response(
        val advantages: List<String>,
        val barcode: String,
        val description: String,
        val details: Details,
        val disadvantages: List<Any>,
        val id: Int,
        val image: String,
        val price: String,
        val rate: Double,
        val rate_details: RateDetails,
        val research_document: String,
        val title: String
    ) {
        data class Details(
            val yearOfManufacture: String,
            val yearOfResearch: String,
            val manufacturer: String,
            val producer: String,
            val composition: String,
            val barcode: String
        )

        data class RateDetails(
            val quality: Double,
            val organoleptics: Double,
            val physicalChemicalParameters: Int
        )
    }
}