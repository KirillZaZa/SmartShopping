package com.conlage.smartshopping.model.data.usecase.impl

import com.conlage.smartshopping.model.data.local.db.entity.Product
import com.conlage.smartshopping.model.data.local.db.entity.ProductList
import com.conlage.smartshopping.model.data.repository.impl.ShoppingRepositoryImpl
import com.conlage.smartshopping.model.data.repository.resultwrapper.RepositoryResponse
import com.conlage.smartshopping.model.data.usecase.ProductListUseCase
import com.conlage.smartshopping.model.data.usecase.exception.FailureException
import com.conlage.smartshopping.model.data.usecase.wrapper.UseCaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductListUseCaseImpl @Inject constructor(
    private val repositoryImpl: ShoppingRepositoryImpl
) : ProductListUseCase {


    override suspend fun getProductList(query: String, page: Int): UseCaseResult<ProductList> {
        return try {
            val productList = withContext(Dispatchers.IO) {
                when(val result = repositoryImpl.getProductList(query, page)){
                    is RepositoryResponse.Success<ProductList> -> result.response
                    is RepositoryResponse.Failure<ProductList> -> throw FailureException()
                }
            }
            UseCaseResult.Response(productList)
        } catch (e: Throwable) {
            e.printStackTrace()
            UseCaseResult.Error(e)
        }
    }

    override suspend fun getProductListFromDb(): UseCaseResult<List<Product>> {
        return try {
            val productList = withContext(Dispatchers.IO) {
                when(val result = repositoryImpl.getProductListFromDb()){
                    is RepositoryResponse.Success<List<Product>> -> result.response
                    is RepositoryResponse.Failure<List<Product>> -> throw FailureException()
                }
            }
            UseCaseResult.Response(productList)
        } catch (e: Throwable) {
            e.printStackTrace()
            UseCaseResult.Error(e)
        }
    }
}