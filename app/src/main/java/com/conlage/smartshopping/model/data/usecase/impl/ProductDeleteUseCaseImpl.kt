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


    override suspend fun deleteProductFromDb(product: Product) {
        try {
            withContext(Dispatchers.IO) {
                repositoryImpl.deleteProductFromDb(product)
            }

        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override suspend fun deleteProductFromDbById(productId: Int, productImage: String) {
        try {
            withContext(Dispatchers.IO) {
                repositoryImpl.deleteProductFromDbById(productId, productImage)
            }

        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }


}
