package com.conlage.smartshopping.model.data.usecase.impl

import com.conlage.smartshopping.model.data.local.db.entity.ShopItem
import com.conlage.smartshopping.model.data.repository.impl.ShoppingRepositoryImpl
import com.conlage.smartshopping.model.data.usecase.ProductUpdateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductUpdateUseCaseImpl @Inject constructor(
   private val repositoryImpl: ShoppingRepositoryImpl
) : ProductUpdateUseCase {


    override suspend fun updateProductInDb(shopItem: ShopItem) {
        try {
            withContext(Dispatchers.IO){
                repositoryImpl.updateProductInDb(shopItem)
            }
        }catch (e: Throwable){
            e.printStackTrace()
        }
    }

}