package com.conlage.smartshopping.model.di

import android.app.Activity
import android.app.Application
import com.conlage.smartshopping.model.di.modules.MainModule
import com.conlage.smartshopping.view.presentation.MainActivity
import com.conlage.smartshopping.viewmodel.impl.ProductViewModelImpl
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [MainModule::class])
interface AppShoppingComponent {



    fun inject(activity: MainActivity)

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppShoppingComponent
    }

}