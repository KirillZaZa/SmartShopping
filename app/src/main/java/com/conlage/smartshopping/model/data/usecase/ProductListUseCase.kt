package com.conlage.smartshopping.model.data.usecase

import com.conlage.smartshopping.model.data.usecase.wrapper.UseCaseResult

interface ProductListUseCase {

    suspend fun getProductList(query: String, page: Int): UseCaseResult

    suspend fun getProductListFromDb(): UseCaseResult


}