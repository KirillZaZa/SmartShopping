package com.conlage.smartshopping.model.data.media.resultwrapper

sealed class LoadResult{

    data class Success<T>(val response: T): LoadResult()

    data class Failure<T : Throwable>(val throwable: T): LoadResult()

}
