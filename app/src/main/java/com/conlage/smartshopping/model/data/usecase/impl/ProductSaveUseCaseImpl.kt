package com.conlage.smartshopping.model.data.usecase.impl

import android.util.Log
import com.conlage.smartshopping.model.data.local.db.entity.Product
import com.conlage.smartshopping.model.data.repository.impl.ShoppingRepositoryImpl
import com.conlage.smartshopping.model.data.repository.resultwrapper.RepositoryResponse
import com.conlage.smartshopping.model.data.usecase.ProductSaveUseCase
import com.conlage.smartshopping.model.data.usecase.exception.FailureException
import com.conlage.smartshopping.model.data.usecase.wrapper.UseCaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductSaveUseCaseImpl @Inject constructor(
    private val repositoryImpl: ShoppingRepositoryImpl
) : ProductSaveUseCase {


    override suspend fun saveProductInDb(product: Product): UseCaseResult<Int> {
        return try {
            val isSaved = withContext(Dispatchers.IO){
                when(val result = repositoryImpl.saveProductInDb(product)){
                    is RepositoryResponse.Success<Int> -> result.response
                    is RepositoryResponse.Failure<Int> -> throw FailureException()
                }
            }
            Log.e(this::class.java.simpleName, "saveProductInDb: $isSaved")
            UseCaseResult.Response(isSaved)
        } catch (e: Throwable) {
            e.printStackTrace()
            UseCaseResult.Error(e)
        }
    }


}