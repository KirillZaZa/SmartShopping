package com.conlage.smartshopping.model.data.repository.impl

import android.content.Context
import com.conlage.smartshopping.model.data.local.db.dao.ProductDao
import com.conlage.smartshopping.model.data.local.db.entity.Product
import com.conlage.smartshopping.model.data.media.ImageDownloaderImpl
import com.conlage.smartshopping.model.data.network.service.SmartShoppingService
import com.conlage.smartshopping.model.data.network.resultwrapper.ShoppingResponse
import com.conlage.smartshopping.model.data.repository.ShoppingRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class ShoppingRepositoryImpl @Inject constructor(
    private val api: SmartShoppingService,
    private val scope: CoroutineScope,
    private val context: Context,
    private val productDao: ProductDao,
    private val imageDownloaderImpl: ImageDownloaderImpl
): ShoppingRepository {


    /**
     * 1)отправляем запрос по текущему слову
     *  если нашлись совпадения -> мапим данные, загружаем фотки
     *  если нет -> ошибку в колбек
     *
     */
    override fun getProductList(query: String, page: Int, callback: (ShoppingResponse) -> Unit) {

    }

    /**
     * 1)делаем запрос по текущему id
     * 2)мапим результат
     * 3)генерируем битмапу для barcode
     * 4)возвращаем результат
     */
    override fun getProductById(id: Int, callback: (ShoppingResponse) -> Unit) {

    }

    /**
     * 1)делаем запрос по текущему баркоду
     * 2)мапим результат - если баркод нашелся, нет - ошибка
     * 3)генерируем битмапу для barcode
     */
    override fun getProductByBarcode(barcode: String, callback: (ShoppingResponse) -> Unit) {

    }

    /**
     * 1) сохраняем продукт в базе
     * 2) по колбеку от базы - сохраняем фотку
     * 3) колбек об успешной/провальной транзакции
     */
    override fun saveProductInDb(product: Product, callback: (ShoppingResponse) -> Unit) {

    }

    /**
     *  1)удаляем запись в базе
     *  2)по колбеку от базы удаляем фотку
     *  3)далее колбек об успешной/провальной транзакции
     */
    override fun deleteProductFromDb(product: Product, callback: (ShoppingResponse) -> Unit) {
    }

}