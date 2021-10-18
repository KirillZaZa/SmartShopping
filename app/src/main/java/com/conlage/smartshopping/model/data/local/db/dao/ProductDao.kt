package com.conlage.smartshopping.model.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.conlage.smartshopping.model.data.local.db.dao.base.BaseDao
import com.conlage.smartshopping.model.data.local.db.entity.Product
import kotlinx.coroutines.flow.Flow


@Dao
abstract class ProductDao : BaseDao<ProductDao> {

    @Query("SELECT * FROM product_table")
    abstract fun getProductList(): Flow<List<Product>>


}