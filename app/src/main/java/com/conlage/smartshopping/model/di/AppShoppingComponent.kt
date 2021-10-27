package com.conlage.smartshopping.model.di

import android.app.Activity
import android.app.Application
import com.conlage.smartshopping.viewmodel.impl.ProductViewModelImpl
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component
interface AppShoppingComponent {


    fun inject(productViewModelImpl: ProductViewModelImpl)

    fun inject(activity: Activity)

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppShoppingComponent
    }

}