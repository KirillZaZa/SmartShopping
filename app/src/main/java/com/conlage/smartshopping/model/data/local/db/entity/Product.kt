package com.conlage.smartshopping.model.data.local.db.entity

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(tableName = "product_table")
data class Product(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "product_id")
    var id: Int,

    @ColumnInfo(name = "product_image")
    var image: String, // img path

    @ColumnInfo(name = "product_price")
    var price: String,

    @ColumnInfo(name = "product_rate")
    var rate: Double,

    @ColumnInfo(name = "product_title")
    var title: String,

    @ColumnInfo(name = "product_quantity")
    var quantity: Int = 1,


){
    @Ignore
    var wantBeDeleted: Boolean = false

    @Ignore
    var bitmap: Bitmap? = null
}