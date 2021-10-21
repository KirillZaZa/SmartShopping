package com.conlage.smartshopping.model.data.usecase

import com.conlage.smartshopping.model.data.local.ProductDetails
import com.conlage.smartshopping.model.data.usecase.wrapper.UseCaseResult

interface ProductIdUseCase{



    suspend fun getProductById(id: Int): UseCaseResult<ProductDetails>






}