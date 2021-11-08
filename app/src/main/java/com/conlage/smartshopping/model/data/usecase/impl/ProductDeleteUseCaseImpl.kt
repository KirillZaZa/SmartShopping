package com.conlage.smartshopping.model.data.usecase.impl

import com.conlage.smartshopping.model.data.local.db.entity.ShopItem
import com.conlage.smartshopping.model.data.repository.impl.ShoppingRepositoryImpl
import com.conlage.smartshopping.model.data.usecase.ProductDeleteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductDeleteUseCaseImpl @Inject constructor(
    private val repositoryImpl: ShoppingRepositoryImpl
) : ProductDeleteUseCase {


    override suspend fun deleteProductFromDb(shopItem: ShopItem) {
        try {
            withContext(Dispatchers.IO) {
                repositoryImpl.deleteProductFromDb(shopItem)
            }

        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override suspend fun deleteProductFromDbById(shopItemId: Int) {
        try {
            withContext(Dispatchers.IO) {
                repositoryImpl.deleteProductFromDbById(shopItemId)
            }

        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }


}
