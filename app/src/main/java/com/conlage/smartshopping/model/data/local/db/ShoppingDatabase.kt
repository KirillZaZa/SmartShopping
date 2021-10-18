package com.conlage.smartshopping.model.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.conlage.smartshopping.model.data.local.db.dao.ProductDao
import com.conlage.smartshopping.model.data.local.db.entity.Product


@Database(
    entities = [Product::class],
    version = 1,
    exportSchema = true
)
abstract class ShoppingDatabase : RoomDatabase() {

    abstract fun getProductDao(): ProductDao

}