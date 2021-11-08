package com.conlage.smartshopping.model.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.conlage.smartshopping.model.data.local.Product
import com.conlage.smartshopping.model.data.local.db.dao.ShopItemDao
import com.conlage.smartshopping.model.data.local.db.entity.ShopItem


@Database(
    entities = [ShopItem::class],
    version = 1,
    exportSchema = true
)
abstract class ShoppingDatabase : RoomDatabase() {

    abstract fun getShopItemDao(): ShopItemDao

}