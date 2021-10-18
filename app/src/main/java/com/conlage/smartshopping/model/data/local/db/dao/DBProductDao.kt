package com.conlage.smartshopping.model.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.conlage.smartshopping.model.data.local.db.dao.base.BaseDao


@Dao
abstract class DBProductDao : BaseDao<DBProductDao>{

    @Query("SELECT * FROM product_table")
    abstract fun getProductList(): List<DBProductDao>


}