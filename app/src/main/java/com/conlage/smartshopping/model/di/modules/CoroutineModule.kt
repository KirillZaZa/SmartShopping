package com.conlage.smartshopping.model.di.modules

import android.util.Log
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob


@Module
class CoroutineModule {

    private val handler by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        val errorHandler = CoroutineExceptionHandler { _, throwable ->
            Log.e(
                "Coroutine Scope:",
                "error - ${throwable.localizedMessage}"
            )

        }
        errorHandler
    }

    @Provides
    fun provideHandler(): CoroutineExceptionHandler = handler

}