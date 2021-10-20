package com.conlage.smartshopping.utils.media.resultwrapper

sealed class LoadResult<T>{



    data class Success<T>(val response: T?): LoadResult<T>()

    data class Failure<T>(val throwable: T? = null): LoadResult<T>()

}
