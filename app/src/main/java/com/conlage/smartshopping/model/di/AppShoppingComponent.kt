package com.conlage.smartshopping.model.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component
interface AppShoppingComponent {



    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppShoppingComponent
    }

}