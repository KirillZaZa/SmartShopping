package com.conlage.smartshopping.model.di.modules

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


@Module(includes = [NetworkModule::class, CoroutineModule::class])
abstract class MainModule {
    @Singleton
    @Binds
    abstract fun context(application: Application): Context

}