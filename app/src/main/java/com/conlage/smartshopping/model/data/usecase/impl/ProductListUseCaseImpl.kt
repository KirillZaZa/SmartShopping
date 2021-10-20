package com.conlage.smartshopping.model.data.usecase.impl

import com.conlage.smartshopping.model.data.repository.impl.ShoppingRepositoryImpl
import com.conlage.smartshopping.model.data.repository.resultwrapper.RepositoryResponse
import com.conlage.smartshopping.model.data.usecase.ProductListUseCase
import com.conlage.smartshopping.model.data.usecase.wrapper.UseCaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductListUseCaseImpl @Inject constructor(
    private val repositoryImpl: ShoppingRepositoryImpl
) : ProductListUseCase {


    override suspend fun getProductList(query: String, page: Int): UseCaseResult {
        return try {
            val productList = withContext(Dispatchers.IO) {
                when(val result = repositoryImpl.getProductList(query, page)){
                    is RepositoryResponse.Success<*> -> result.response
                    is RepositoryResponse.Failure<*> -> result.responseError
                }
            }
            UseCaseResult.Response(productList)
        } catch (e: Throwable) {
            e.printStackTrace()
            UseCaseResult.Error(e)
        }
    }

    override suspend fun getProductListFromDb(): UseCaseResult {
        return try {
            val productList = withContext(Dispatchers.IO) {
                when(val result = repositoryImpl.getProductListFromDb()){
                    is RepositoryResponse.Success<*> -> result.response
                    is RepositoryResponse.Failure<*> -> result.responseError
                }
            }
            UseCaseResult.Response(productList)
        } catch (e: Throwable) {
            e.printStackTrace()
            UseCaseResult.Error(e)
        }
    }
}