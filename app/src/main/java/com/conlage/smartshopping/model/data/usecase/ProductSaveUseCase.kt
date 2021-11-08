package com.conlage.smartshopping.model.data.usecase

import com.conlage.smartshopping.model.data.local.Product
import com.conlage.smartshopping.model.data.local.db.entity.ShopItem

interface ProductSaveUseCase {

    suspend fun saveProductInDb(shopItem: ShopItem)


}