package com.conlage.smartshopping.model.data.usecase

import com.conlage.smartshopping.model.data.local.db.entity.ShopItem

interface ProductDeleteUseCase {

    suspend fun deleteProductFromDb(shopItem: ShopItem)

    suspend fun deleteProductFromDbById(shopItemId: Int)

}