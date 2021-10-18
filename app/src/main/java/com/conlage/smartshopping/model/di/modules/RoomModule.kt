package com.conlage.smartshopping.model.di.modules

import android.content.Context
import androidx.room.Room
import com.conlage.smartshopping.R
import com.conlage.smartshopping.model.data.local.db.ShoppingDatabase
import com.conlage.smartshopping.model.data.local.db.dao.ProductDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
object RoomModule {

    @Volatile
    private var INSTANCE: ShoppingDatabase? = null

    @Singleton
    @Provides
    fun provideDatabase(context: Context): ShoppingDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                ShoppingDatabase::class.java,
                context.getString(R.string.database_name)
            ).build()
            INSTANCE = instance
            instance
        }
    }

    @Provides
    fun provideProductDao(database: ShoppingDatabase): ProductDao {
        return database.getProductDao()
    }

}