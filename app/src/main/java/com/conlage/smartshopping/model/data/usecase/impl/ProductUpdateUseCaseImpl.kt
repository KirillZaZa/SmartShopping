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
        try {
            withContext(Dispatchers.IO){
                repositoryImpl.updateProductInDb(product)
            }
        }catch (e: Throwable){
            e.printStackTrace()
        }
    }

}