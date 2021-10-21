package com.conlage.smartshopping.model.data.usecase.impl

import android.util.Log
import com.conlage.smartshopping.model.data.local.db.entity.Product
import com.conlage.smartshopping.model.data.repository.impl.ShoppingRepositoryImpl
import com.conlage.smartshopping.model.data.repository.resultwrapper.RepositoryResponse
import com.conlage.smartshopping.model.data.usecase.ProductDeleteUseCase
import com.conlage.smartshopping.model.data.usecase.exception.FailureException
import com.conlage.smartshopping.model.data.usecase.wrapper.UseCaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductDeleteUseCaseImpl @Inject constructor(
    private val repositoryImpl: ShoppingRepositoryImpl
) : ProductDeleteUseCase {


    override suspend fun deleteProductFromDb(product: Product): UseCaseResult<Int> {
        return try {
            val hasBeenDeleted = withContext(Dispatchers.IO) {
                when (val result = repositoryImpl.deleteProductFromDb(product)) {
                    is RepositoryResponse.Success<Int> -> result.response
                    is RepositoryResponse.Failure<Int> -> throw FailureException()
                }
            }
            Log.e(
                ProductBarCodeUseCaseImpl::class.java.simpleName,
                "deleteProductFromDb: $hasBeenDeleted"
            )
            UseCaseResult.Response(hasBeenDeleted)
        } catch (e: Throwable) {
            e.printStackTrace()
            UseCaseResult.Error(e)
        }
    }

    override suspend fun deleteProductFromDbById(productId: Int, productImage: String): UseCaseResult<Int> {
        return try {
            val hasBeenDeleted = withContext(Dispatchers.IO) {
                when (val result = repositoryImpl.deleteProductFromDbById(productId, productImage)) {
                    is RepositoryResponse.Success<Int> -> result.response
                    is RepositoryResponse.Failure<Int> -> throw FailureException()
                }
            }
            Log.e(
                ProductBarCodeUseCaseImpl::class.java.simpleName,
                "deleteProductFromDb: $hasBeenDeleted"
            )
            UseCaseResult.Response(hasBeenDeleted)
        }catch (e : Throwable){
            e.printStackTrace()
            UseCaseResult.Error(e)
        }
    }


}
