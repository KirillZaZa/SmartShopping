package com.conlage.smartshopping.model.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.conlage.smartshopping.model.data.local.db.dao.DBProductDao
import com.conlage.smartshopping.model.data.local.db.entity.DBProduct


@Database(
    entities = [DBProduct::class],
    version = 1,
    exportSchema = true
)
abstract class ShoppingDatabase : RoomDatabase() {

    abstract fun getProductDao(): DBProductDao

}