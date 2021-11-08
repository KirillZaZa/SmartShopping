package com.conlage.smartshopping.model.data.usecase.impl

import com.conlage.smartshopping.model.data.local.db.entity.ShopItem
import com.conlage.smartshopping.model.data.repository.impl.ShoppingRepositoryImpl
import com.conlage.smartshopping.model.data.usecase.ProductSaveUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductSaveUseCaseImpl @Inject constructor(
    private val repositoryImpl: ShoppingRepositoryImpl
) : ProductSaveUseCase {


    override suspend fun saveProductInDb(shopItem: ShopItem) {
        try {
            withContext(Dispatchers.IO){
                repositoryImpl.saveProductInDb(shopItem)
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }


}