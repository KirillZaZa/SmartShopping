package com.conlage.smartshopping.model.data.repository

import com.conlage.smartshopping.model.data.local.db.entity.Product
import com.conlage.smartshopping.model.data.repository.resultwrapper.RepositoryResponse

interface ShoppingRepository {


    suspend fun getProductList(query: String, page: Int, ): RepositoryResponse

    suspend fun getProductById(id: Int): RepositoryResponse

    suspend fun getProductByBarcode(barcode: String): RepositoryResponse

    suspend fun saveProductInDb(product: Product): RepositoryResponse

    suspend fun deleteProductFromDb(product: Product): RepositoryResponse

    suspend fun getProductListFromDb(): RepositoryResponse


}