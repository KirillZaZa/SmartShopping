package com.conlage.smartshopping.model.data.local

import android.graphics.Bitmap


data class Product(
    val id: Int,
    val image: String,
    val price: String,
    val rate: Double,
    val title: String,
    var bitmap: Bitmap? = null
)
