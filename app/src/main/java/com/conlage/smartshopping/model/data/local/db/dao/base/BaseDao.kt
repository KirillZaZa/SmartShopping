package com.conlage.smartshopping.model.data.local.db.dao.base

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<in T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg obj: T): Int

    @Delete
    suspend fun delete(obj: T): Int

    @Update
    suspend fun update(obj: T): Int

}