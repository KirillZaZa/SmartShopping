package com.conlage.smartshopping.model.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.conlage.smartshopping.model.data.local.db.dao.base.BaseDao
import com.conlage.smartshopping.model.data.local.db.entity.ShopItem



@Dao
abstract class ShopItemDao : BaseDao<ShopItem> {

    @Query("SELECT * FROM shop_item_table")
    abstract fun getProductList(): List<ShopItem>

    @Query("DELETE FROM shop_item_table WHERE item_id LIKE :itemId")
    abstract fun deleteProductById(itemId: Int)


}