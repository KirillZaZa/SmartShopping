package com.conlage.smartshopping.model.data.usecase.impl

import com.conlage.smartshopping.model.data.local.ProductDetails
import com.conlage.smartshopping.model.data.repository.impl.ShoppingRepositoryImpl
import com.conlage.smartshopping.model.data.repository.resultwrapper.RepositoryResponse
import com.conlage.smartshopping.model.data.usecase.ProductIdUseCase
import com.conlage.smartshopping.model.data.usecase.exception.FailureException
import com.conlage.smartshopping.model.data.usecase.wrapper.UseCaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductIdUseCaseImpl @Inject constructor(
    private val repositoryImpl: ShoppingRepositoryImpl
) : ProductIdUseCase {


    override suspend fun getProductById(id: Int): UseCaseResult<ProductDetails> {
        return try {
            val product = withContext(Dispatchers.IO) {
                when (val result = repositoryImpl.getProductById(id)) {
                    is RepositoryResponse.Success<ProductDetails> -> {
                        result.response
                    }
                    is RepositoryResponse.Failure<ProductDetails> -> {
                        throw FailureException()
                    }
                }
            }
            UseCaseResult.Response(product)
        } catch (e: Throwable) {
            e.printStackTrace()
            UseCaseResult.Error(e)
        }
    }


}