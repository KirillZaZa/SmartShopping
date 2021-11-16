package com.conlage.smartshopping.model.data.mapper

import android.util.Log
import com.conlage.smartshopping.model.data.local.ProductDetails
import com.conlage.smartshopping.model.data.local.Product
import com.conlage.smartshopping.model.data.local.ProductList
import com.conlage.smartshopping.model.data.network.dto.NetworkProduct
import com.conlage.smartshopping.model.data.network.dto.NetworkProductList


fun Double.formatRate(): Double{
    return Math.round(this * 100) / 100.0
}

fun NetworkProductList.ProductItem.toProduct() =
    Product(
        id = this.id,
        rate = if (this.rate == null) 0.0 else this.rate.formatRate() ,
        title = this.title ?: "Неизвестный товар",
        image = this.image ?: "" ,
        price = this.price ?: "нет цены"
    )

fun List<NetworkProductList.ProductItem>.mapToProductList(): ProductList {
    return ProductList(
        list = this.map { it.toProduct() }
    )
}

fun NetworkProduct.toProductDetails(): ProductDetails{
    with(this.response){
        return ProductDetails(
            this.advantages,
            this.barcode,
            this.description,
            this.details,
            this.disadvantages,
            this.id,
            this.image,
            this.price,
            this.rate,
            this.rate_details,
            this.research_document,
            this.title
        )
    }
}