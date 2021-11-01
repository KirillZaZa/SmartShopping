package com.conlage.smartshopping.model.data.local

import android.graphics.Bitmap
import com.conlage.smartshopping.model.data.network.dto.NetworkProduct

data class ProductDetails(
    val advantages: List<String>,
    val barcode: String,
    val description: String,
    val details: NetworkProduct.Response.Details,
    val disadvantages: List<Any>,
    val id: Int,
    val image: String,
    val price: String,
    val rate: Double,
    val rate_details: NetworkProduct.Response.RateDetails,
    val research_document: String,
    val title: String,
    var barcodeImg: Bitmap? = null,
    var bitmap: Bitmap? = null
){

}
