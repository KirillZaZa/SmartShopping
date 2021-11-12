package com.conlage.smartshopping.model.di.modules

import android.content.Context
import com.conlage.smartshopping.R
import com.conlage.smartshopping.model.data.network.service.SmartShoppingService
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module
class NetworkModule {


    @Provides
    fun provideSmartShoppingService(context: Context): SmartShoppingService {

        val cache = Cache(
            directory = context.cacheDir,
            maxSize = 10L * 1024 * 1024
        )

        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(2, TimeUnit.SECONDS)
            .callTimeout(3, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor())
            .cache(cache)
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(SmartShoppingService::class.java)
    }


}