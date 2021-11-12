package com.conlage.smartshopping.model.data.repository.impl

import android.util.Log
import com.conlage.smartshopping.model.data.local.ProductDetails
import com.conlage.smartshopping.model.data.local.db.ShoppingDatabase
import com.conlage.smartshopping.model.data.local.Product
import com.conlage.smartshopping.model.data.local.ProductList
import com.conlage.smartshopping.model.data.local.db.entity.ShopItem
import com.conlage.smartshopping.model.data.mapper.mapToProductList
import com.conlage.smartshopping.model.data.mapper.toProductDetails
import com.conlage.smartshopping.utils.media.ImageDownloaderImpl
import com.conlage.smartshopping.utils.media.resultwrapper.LoadResult
import com.conlage.smartshopping.model.data.repository.resultwrapper.RepositoryResponse
import com.conlage.smartshopping.model.data.network.service.SmartShoppingService
import com.conlage.smartshopping.model.data.repository.ShoppingRepository
import com.conlage.smartshopping.model.data.usecase.exception.FailureException
import com.conlage.smartshopping.utils.barcode.BarcodeGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException
import javax.inject.Inject

class ShoppingRepositoryImpl @Inject constructor(
    private val api: SmartShoppingService,
    private val db: ShoppingDatabase,
    private val imageDownloader: ImageDownloaderImpl,
    private val barcodeGenerator: BarcodeGenerator
) : ShoppingRepository {


    override suspend fun getProductList(query: String, page: Int): RepositoryResponse<ProductList> {
        val networkProductList = api.getProductListByName(query, page)
        Log.e("ShoppingRepository", "$networkProductList ")
        return if (!networkProductList.response.isNullOrEmpty()) {

            val mappedList = networkProductList.response.mapToProductList()

            val result = withContext(Dispatchers.IO) {

                mappedList.list.forEach { product ->

                    product.bitmap =
                        imageDownloader.downloadImageFromNetwork(product.image).response ?: null

                }
                Log.e("ShoppingRepository", "$mappedList ")

                mappedList
            }


            RepositoryResponse.Success(result)

        } else RepositoryResponse.Failure(Throwable())
    }


    override suspend fun getProductById(id: Int): RepositoryResponse<ProductDetails> {
        val networkProduct = api.getProductDetailsById(id)
        Log.e("ShoppingRepository", "$networkProduct")

        val productDetails = networkProduct.toProductDetails()
        with(productDetails) {

            this.barcodeImg =
                if (this.barcode == null) null
                else barcodeGenerator.generateBarcodeBitmap(this.barcode)
            this.bitmap = withContext(Dispatchers.IO) {
                when (val result =
                    imageDownloader.downloadImageForPage(this@with.image)) {
                    is LoadResult.Success -> result.response
                    is LoadResult.Failure -> result.throwable
                    else -> throw IllegalArgumentException()
                }
            }
        }

        return RepositoryResponse.Success(productDetails)
    }


    override suspend fun getProductByBarcode(barcode: String): RepositoryResponse<ProductDetails> {
        return try {
            val networkProduct = api.getProductDetailsByBarcode(barcode)
            val productDetails = networkProduct.toProductDetails()
            with(productDetails) {
                this.bitmap = withContext(Dispatchers.IO) {
                    when (val result =
                        imageDownloader.downloadImageForPage(this@with.image)) {
                        is LoadResult.Success -> result.response
                        is LoadResult.Failure -> result.throwable
                        else -> throw IllegalArgumentException()
                    }
                }
                this.barcodeImg =
                    if (this.barcode == null) null
                    else barcodeGenerator.generateBarcodeBitmap(this.barcode)
            }
            RepositoryResponse.Success(productDetails)
        } catch (e: Throwable) {
            e.printStackTrace()
            RepositoryResponse.Failure(e)
        }
    }


    override suspend fun saveProductInDb(shopItem: ShopItem) {
        try {
            db.getShopItemDao().insert(shopItem)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }


    override suspend fun deleteProductFromDb(shopItem: ShopItem) {
        try {
            db.getShopItemDao().delete(shopItem)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override suspend fun deleteProductFromDbById(shopItemId: Int) {
        try {
            db.getShopItemDao().deleteProductById(shopItemId)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override suspend fun updateProductInDb(shopItem: ShopItem) {
        try {
            db.getShopItemDao().update(shopItem)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override suspend fun getProductListFromDb(callback: (List<ShopItem>) -> Unit) {
        val list = db.getShopItemDao().getProductList()

        callback(list)
    }


}
