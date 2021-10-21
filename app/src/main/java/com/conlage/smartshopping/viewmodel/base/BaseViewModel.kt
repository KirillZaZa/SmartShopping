package com.conlage.smartshopping.viewmodel.base

import android.util.Log
import androidx.annotation.UiThread
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers

abstract class BaseViewModel<T>(
    initState: T,
) : ViewModel() {


    val dispatcherMain = Dispatchers.Main

    val errHandler by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        val errorHandler = CoroutineExceptionHandler { _, throwable ->
            Log.e(
                "Coroutine Scope:",
                "error - ${throwable.localizedMessage}"
            )

        }
        errorHandler
    }


    protected val state: MutableState<T> = mutableStateOf(
        value = initState
    )

    protected val currentValue
        get() = state.value


    val currentState: State<T>
        get() = state



    @UiThread
    protected inline fun updateState(
        update: (currentState: T) -> T
    ) {
        val updatedState: T = update(currentState.value)
        state.value = updatedState
    }


    protected fun <S> subscribeOnDataSource(
        source: S,
        onChanged: (newValue: S, currentState: T) -> T?
    ) {
        state.value = onChanged(source, currentState.value) ?: return
    }


}