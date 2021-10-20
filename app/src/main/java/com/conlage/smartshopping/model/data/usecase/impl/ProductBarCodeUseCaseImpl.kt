package com.conlage.smartshopping.model.data.usecase.impl

import com.conlage.smartshopping.model.data.repository.impl.ShoppingRepositoryImpl
import com.conlage.smartshopping.model.data.repository.resultwrapper.RepositoryResponse
import com.conlage.smartshopping.model.data.usecase.ProductBarcodeUseCase
import com.conlage.smartshopping.model.data.usecase.wrapper.UseCaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductBarCodeUseCaseImpl @Inject constructor(
    private val repositoryImpl: ShoppingRepositoryImpl
) : ProductBarcodeUseCase {

    override suspend fun getProductByBarcode(barcode: String): UseCaseResult {
        return try {
            val product = withContext(Dispatchers.IO) {
                when (val result = repositoryImpl.getProductByBarcode(barcode)) {
                    is RepositoryResponse.Success<*> -> result.response
                    is RepositoryResponse.Failure<*> -> result.responseError
                }
            }
            UseCaseResult.Response(product)
        } catch (e: Throwable) {
            e.printStackTrace()
            UseCaseResult.Error(e)
        }
    }
}