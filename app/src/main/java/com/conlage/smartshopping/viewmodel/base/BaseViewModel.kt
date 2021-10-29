package com.conlage.smartshopping.viewmodel.base

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.UiThread
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.conlage.smartshopping.viewmodel.events.BaseEvent
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

    val event = MutableLiveData<Event<BaseEvent>>()

    fun handleEvent(content: BaseEvent){
        event.postValue(Event(content))
    }



   val state: MutableState<T> = mutableStateOf(
        value = initState
    )

    val currentValue
        get() = state.value


    @UiThread
    protected inline fun updateState(
        update: (currentState: T) -> T
    ) {
        val updatedState: T = update(currentValue)
        state.value = updatedState
    }


    protected fun <S> subscribeOnDataSource(
        source: S,
        onChanged: (newValue: S, currentState: T) -> T?
    ) {
        state.value = onChanged(source, currentValue) ?: return
    }

    fun observeEvents(owner: LifecycleOwner, onEvent: (event: BaseEvent) -> Unit){
        event.observe(owner, EventObserver{onEvent(it)})
    }



}


class Event<out E>(private val content: E) {
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): E? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}


class EventObserver<E>(private val onEventUnhandledContent: (E) -> Unit) : Observer<Event<E>> {
    override fun onChanged(e: Event<E>?) {
        e?.getContentIfNotHandled()?.let {
            onEventUnhandledContent(it)
        }
    }
}