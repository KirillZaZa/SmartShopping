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

    @ColumnInfo(name = "product_id")
    val image: String, // img path

    @ColumnInfo(name = "product_id")
    val price: String,

    @ColumnInfo(name = "product_id")
    val rate: Int,

    @ColumnInfo(name = "product_id")
    val title: String,

    @Ignore
    var bitmap: Bitmap? = null
)