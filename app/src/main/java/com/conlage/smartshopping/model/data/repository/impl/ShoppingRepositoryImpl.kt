package com.conlage.smartshopping.model.data.repository.impl

import com.conlage.smartshopping.model.data.network.service.SmartShoppingService
import com.conlage.smartshopping.model.data.network.wrapper.ShoppingResponse
import com.conlage.smartshopping.model.data.repository.ShoppingRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class ShoppingRepositoryImpl @Inject constructor(
    private val api: SmartShoppingService,
    private val scope: CoroutineScope
): ShoppingRepository {


    override fun getProductList(page: Int, callback: (ShoppingResponse) -> Unit) {

    }

    override fun getProductById(callback: (ShoppingResponse) -> Unit) {

    }

    override fun getProductByBarcodeFromNetwork(callback: (ShoppingResponse) -> Unit) {

    }

    override fun getProductByBarcodeFromDb(callback: (ShoppingResponse) -> Unit) {

    }
}