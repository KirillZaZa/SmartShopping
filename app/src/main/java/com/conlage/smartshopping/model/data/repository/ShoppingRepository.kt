package com.conlage.smartshopping.model.data.repository

import com.conlage.smartshopping.model.data.local.ProductDetails
import com.conlage.smartshopping.model.data.local.Product
import com.conlage.smartshopping.model.data.local.ProductList
import com.conlage.smartshopping.model.data.local.db.entity.ShopItem
import com.conlage.smartshopping.model.data.repository.resultwrapper.RepositoryResponse

interface ShoppingRepository {


    suspend fun getProductList(query: String, page: Int, ): RepositoryResponse<ProductList>

    suspend fun getProductById(id: Int): RepositoryResponse<ProductDetails>

    suspend fun getProductByBarcode(barcode: String): RepositoryResponse<ProductDetails>

    suspend fun saveProductInDb(shopItem: ShopItem)

    suspend fun deleteProductFromDb(shopItem: ShopItem)

    suspend fun deleteProductFromDbById(shopItemId: Int)

    suspend fun updateProductInDb(shopItem: ShopItem)

    suspend fun getProductListFromDb(callback: (List<ShopItem>) -> Unit)



}