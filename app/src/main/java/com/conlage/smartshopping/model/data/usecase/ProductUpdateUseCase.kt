package com.conlage.smartshopping.model.data.usecase

import com.conlage.smartshopping.model.data.local.Product
import com.conlage.smartshopping.model.data.local.db.entity.ShopItem

interface ProductUpdateUseCase {

    suspend fun updateProductInDb(shopItem: ShopItem)

}