package com.conlage.smartshopping.model.data.usecase

import com.conlage.smartshopping.model.data.local.db.entity.Product
import com.conlage.smartshopping.model.data.local.db.entity.ProductList
import com.conlage.smartshopping.model.data.usecase.wrapper.UseCaseResult

interface ProductListUseCase {

    suspend fun getProductList(query: String, page: Int): UseCaseResult<ProductList>

    suspend fun getProductListFromDb(): UseCaseResult<List<Product>>


}