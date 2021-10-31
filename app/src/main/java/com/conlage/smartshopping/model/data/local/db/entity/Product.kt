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
    val id: Int,

    @ColumnInfo(name = "product_image")
    val image: String, // img path

    @ColumnInfo(name = "product_price")
    val price: String,

    @ColumnInfo(name = "product_rate")
    val rate: Double,

    @ColumnInfo(name = "product_title")
    val title: String,

    @ColumnInfo(name = "product_quantity")
    val quantity: Int = 0,


){
    @Ignore
    var wantBeDeleted: Boolean = false

    @Ignore
    var bitmap: Bitmap? = null

}


data class ProductState(

    var id: Int,

    var image: String, // img path

    var price: String,

    var rate: Double,

    var title: String,

    var quantity: Int = 0,


    ){
    var wantBeDeleted: Boolean = false

    var bitmap: Bitmap? = null

}