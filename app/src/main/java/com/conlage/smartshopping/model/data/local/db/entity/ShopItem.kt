package com.conlage.smartshopping.model.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "shop_item_table")
data class ShopItem(

    @PrimaryKey()
    @ColumnInfo(name = "item_id")
    val id: String = UUID.randomUUID().toString(),


    @ColumnInfo(name = "item_title")
    val title: String


){

    @Ignore
    var wantBeDeleted = false

}
