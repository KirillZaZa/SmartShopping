package com.conlage.smartshopping.model.data.repository

import com.conlage.smartshopping.model.data.local.ProductDetails
import com.conlage.smartshopping.model.data.local.db.entity.Product
import com.conlage.smartshopping.model.data.local.db.entity.ProductList
import com.conlage.smartshopping.model.data.repository.resultwrapper.RepositoryResponse

interface ShoppingRepository {


    suspend fun getProductList(query: String, page: Int, ): RepositoryResponse<ProductList>

    suspend fun getProductById(id: Int): RepositoryResponse<ProductDetails>

    suspend fun getProductByBarcode(barcode: String): RepositoryResponse<ProductDetails>

    suspend fun saveProductInDb(product: Product)

    suspend fun deleteProductFromDb(product: Product)

    suspend fun deleteProductFromDbById(productId: Int, productImage: String)

    suspend fun updateProductInDb(product: Product)

    suspend fun getProductListFromDb(): RepositoryResponse<List<Product>>



}