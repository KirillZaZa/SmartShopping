package com.conlage.smartshopping.model.data.usecase

import com.conlage.smartshopping.model.data.local.db.entity.Product

interface ProductUpdateUseCase {

    suspend fun updateProductInDb(product: Product)

}