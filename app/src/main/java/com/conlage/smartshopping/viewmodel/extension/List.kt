package com.conlage.smartshopping.viewmodel.extension

import com.conlage.smartshopping.model.data.local.db.entity.Product

fun List<Product>.getProductById(productId: Int): Product{
    return this.filter { it.id == productId }[0]
}