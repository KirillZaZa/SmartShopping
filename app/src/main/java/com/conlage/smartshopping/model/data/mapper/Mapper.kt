package com.conlage.smartshopping.model.data.mapper

import com.conlage.smartshopping.model.data.local.ProductDetails
import com.conlage.smartshopping.model.data.local.db.entity.Product
import com.conlage.smartshopping.model.data.local.db.entity.ProductList
import com.conlage.smartshopping.model.data.network.dto.NetworkProduct
import com.conlage.smartshopping.model.data.network.dto.NetworkProductList


fun NetworkProductList.ProductItem.toProduct() =
    Product(
        id = this.id,
        rate = this.rate,
        title = this.title,
        image = this.image,
        price = this.price
    )

fun List<NetworkProductList.ProductItem>.mapToProductList(): ProductList{
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