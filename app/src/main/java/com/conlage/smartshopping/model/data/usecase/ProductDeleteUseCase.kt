package com.conlage.smartshopping.model.data.usecase

import com.conlage.smartshopping.model.data.local.db.entity.Product
import com.conlage.smartshopping.model.data.usecase.wrapper.UseCaseResult

interface ProductDeleteUseCase {

    suspend fun deleteProductFromDb(product: Product): UseCaseResult<Int>

    suspend fun deleteProductFromDbById(productId: Int, productImage: String): UseCaseResult<Int>

}