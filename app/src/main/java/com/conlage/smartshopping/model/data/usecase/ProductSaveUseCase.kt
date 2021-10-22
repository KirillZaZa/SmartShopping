package com.conlage.smartshopping.model.data.usecase

import com.conlage.smartshopping.model.data.local.db.entity.Product
import com.conlage.smartshopping.model.data.usecase.wrapper.UseCaseResult

interface ProductSaveUseCase {

    suspend fun saveProductInDb(product: Product)


}