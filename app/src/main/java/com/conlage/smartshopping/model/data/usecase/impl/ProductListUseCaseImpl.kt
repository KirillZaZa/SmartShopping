package com.conlage.smartshopping.model.data.usecase.impl

import android.util.Log
import com.conlage.smartshopping.model.data.local.Product
import com.conlage.smartshopping.model.data.local.ProductList
import com.conlage.smartshopping.model.data.local.db.entity.ShopItem
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
                when(val result = repositoryImpl.getProductList(query.trim(), page)){
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

    override suspend fun getProductListFromDb(): UseCaseResult<List<ShopItem>> {
        return try {
            Log.e("ProductUseCase", "getting..", )
            val productList = withContext(Dispatchers.IO) {
                var list: List<ShopItem>? = null
                repositoryImpl.getProductListFromDb {
                    list = it
                }
                Log.e("ProductUseCase", "getting..", )
                list
            }

            Log.e("ProductUseCase", "$productList", )
            UseCaseResult.Response(productList!!)
        } catch (e: Throwable) {
            e.printStackTrace()
            Log.e("ProductUseCase", "catch", )

            UseCaseResult.Error(e)
        }
    }
}