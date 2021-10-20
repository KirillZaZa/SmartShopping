package com.conlage.smartshopping.model.data.usecase

import com.conlage.smartshopping.model.data.usecase.wrapper.UseCaseResult

interface ProductBarcodeUseCase {

    suspend fun getProductByBarcode(barcode: String): UseCaseResult


}