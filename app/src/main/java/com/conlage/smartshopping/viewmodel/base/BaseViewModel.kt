package com.conlage.smartshopping.viewmodel.base

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers

abstract class BaseViewModel<T>(
    initState: T,
): ViewModel() {


    val dispatcherMain = Dispatchers.Main

    val handler by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        val errorHandler = CoroutineExceptionHandler { _, throwable ->
            Log.e(
                "Coroutine Scope:",
                "error - ${throwable.localizedMessage}"
            )

        }
        errorHandler
    }




// mutablestates
// observe mutable states
// update states

}