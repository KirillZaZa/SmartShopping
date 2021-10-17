package com.conlage.smartshopping.application;

import android.app.Application
import android.content.Context
import com.conlage.smartshopping.model.di.AppShoppingComponent
import com.conlage.smartshopping.model.di.DaggerAppShoppingComponent


class SmartShoppingApplication : Application() {

    lateinit var appShoppingComponent: AppShoppingComponent

    override fun onCreate() {
        super.onCreate()

        appShoppingComponent = DaggerAppShoppingComponent
            .builder()
            .application(this)
            .build()
    }

}

val Context.appShoppingComponent: AppShoppingComponent
    get() = when (this) {
        is SmartShoppingApplication -> appShoppingComponent
        else -> this.applicationContext.appShoppingComponent
    }
