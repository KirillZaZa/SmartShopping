package com.conlage.smartshopping.model.data.repository.impl

import android.util.Log
import com.conlage.smartshopping.model.data.local.ProductDetails
import com.conlage.smartshopping.model.data.local.db.ShoppingDatabase
import com.conlage.smartshopping.model.data.local.db.dao.ProductDao
import com.conlage.smartshopping.model.data.local.db.entity.Product
import com.conlage.smartshopping.model.data.local.db.entity.ProductList
import com.conlage.smartshopping.model.data.mapper.mapToProductList
import com.conlage.smartshopping.model.data.mapper.toProductDetails
import com.conlage.smartshopping.utils.media.ImageDownloaderImpl
import com.conlage.smartshopping.utils.media.resultwrapper.LoadResult
import com.conlage.smartshopping.model.data.network.dto.ResponseError
import com.conlage.smartshopping.model.data.repository.resultwrapper.RepositoryResponse
import com.conlage.smartshopping.model.data.network.service.SmartShoppingService
import com.conlage.smartshopping.model.data.repository.ShoppingRepository
import com.conlage.smartshopping.model.data.usecase.exception.FailureException
import com.conlage.smartshopping.utils.barcode.BarcodeGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException
import javax.inject.Inject

class ShoppingRepositoryImpl @Inject constructor(
    private val api: SmartShoppingService,
    private val db: ShoppingDatabase,
    private val imageDownloader: ImageDownloaderImpl,
    private val barcodeGenerator: BarcodeGenerator
) : ShoppingRepository {


    /**
     * 1)отправляем запрос по текущему слову
     *  если нашлись совпадения -> мапим данные, загружаем фотки
     *  если нет -> ошибку в колбек
     *
     */
    override suspend fun getProductList(query: String, page: Int): RepositoryResponse<ProductList> {
        val networkProductList = api.getProductListByName(query, page)
        return if (!networkProductList.response.isNullOrEmpty()) {

            val mappedList = networkProductList.response.mapToProductList()

            val result = withContext(Dispatchers.IO) {

                mappedList.list.forEach { product ->

                    product.bitmap = withContext(Dispatchers.IO) {
                        when (val result =
                            imageDownloader.downloadImageFromNetwork(product.image)) {
                            is LoadResult.Success -> result.response
                            is LoadResult.Failure -> result.throwable
                            else -> throw FailureException()
                        }
                    }

                }

                mappedList
            }
            RepositoryResponse.Success(result)

        } else RepositoryResponse.Failure(Throwable())
    }

    /**
     * 1)делаем запрос по текущему id
     * 2)мапим результат
     * 3)генерируем битмапу для barcode
     * 4)возвращаем результат
     */
    override suspend fun getProductById(id: Int): RepositoryResponse<ProductDetails> {
        val networkProduct = api.getProductDetailsById(id)
        val productDetails = networkProduct.toProductDetails()
        with(productDetails) {
            this.barcodeImg = barcodeGenerator.generateBarcodeBitmap(this.barcode)
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

    /**
     * 1)делаем запрос по текущему баркоду
     * 2)мапим результат - если баркод нашелся, нет - ошибка
     * 3)генерируем битмапу для barcode
     */
    override suspend fun getProductByBarcode(barcode: String): RepositoryResponse<ProductDetails> {
        return try {
            val networkProduct = api.getProductDetailsByBarcode(barcode)
            val productDetails = networkProduct.toProductDetails()
            with(productDetails) {
                this.barcodeImg = barcodeGenerator.generateBarcodeBitmap(this.barcode)
            }
            RepositoryResponse.Success(productDetails)
        } catch (e: Throwable) {
            e.printStackTrace()
            RepositoryResponse.Failure(e)
        }
    }

    /**
     * 1) сохраняем продукт в базе
     * 2) по колбеку от базы - сохраняем фотку
     * 3) колбек об успешной/провальной транзакции
     */
    override suspend fun saveProductInDb(product: Product) {
        try {
            if (product.bitmap != null ){
                imageDownloader.saveImageToInternalStorage(product.bitmap!!, "${product.id}")
            }
            db.getProductDao().insert(product)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    /**
     *  1)удаляем запись в базе
     *  2)по колбеку от базы удаляем фотку
     *  3)далее колбек об успешной/провальной транзакции
     */
    override suspend fun deleteProductFromDb(product: Product) {
        try {
            imageDownloader.deleteImageFromInternalStorage("${product.id}")
            db.getProductDao().delete(product)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override suspend fun deleteProductFromDbById(productId: Int, productImage: String) {
        try {
            imageDownloader.deleteImageFromInternalStorage(productImage)
            db.getProductDao().deleteProductById(productId)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override suspend fun updateProductInDb(product: Product) {
        try {
            db.getProductDao().update(product)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override suspend fun getProductListFromDb(callback: (List<Product>) -> Unit) {
        val list = db.getProductDao().getProductList()
            .map { product ->
                product.bitmap = when (val loadResult =
                    imageDownloader.loadImageFromInternalStorage("${product.id}")) {
                    is LoadResult.Success -> loadResult.response
                    is LoadResult.Failure -> loadResult.throwable
                    else -> throw IllegalArgumentException()
                }
                product
            }
        Log.e("List", "$list")

        callback(list)

    }



}
