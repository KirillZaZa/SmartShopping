package com.conlage.smartshopping.viewmodel.extension

import com.conlage.smartshopping.model.data.local.db.entity.Product
import java.util.*

fun List<Product>.getProductById(productId: Int): Product{
    return this.filter { it.id == productId }[0]
}


fun List<Product>.containsId(productId: Int): Boolean{
    val idList = LinkedList<Int>()
    this.forEach { product->
        idList.add(product.id)
    }
    return idList.contains(productId)
}