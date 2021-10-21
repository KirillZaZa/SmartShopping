package com.conlage.smartshopping.model.data.usecase.impl

import com.conlage.smartshopping.model.data.local.db.entity.Product
import com.conlage.smartshopping.model.data.repository.impl.ShoppingRepositoryImpl
import com.conlage.smartshopping.model.data.repository.resultwrapper.RepositoryResponse
import com.conlage.smartshopping.model.data.usecase.ProductUpdateUseCase
import com.conlage.smartshopping.model.data.usecase.exception.FailureException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductUpdateUseCaseImpl @Inject constructor(
   private val repositoryImpl: ShoppingRepositoryImpl
) : ProductUpdateUseCase {


    override suspend fun updateProductInDb(product: Product) {
        return try {
            withContext(Dispatchers.IO){
                when(repositoryImpl.updateProductInDb(product)){
                    is RepositoryResponse.Success -> return@withContext
                    else -> throw FailureException()
                }
            }
        }catch (e: Throwable){
            e.printStackTrace()
        }
    }

}