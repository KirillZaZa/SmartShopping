package com.conlage.smartshopping.model.data.usecase.wrapper

sealed class UseCaseResult<T>{

    data class Response<T>(val value: T): UseCaseResult<T>()

    data class Error<T>(val throwable: Throwable): UseCaseResult<T>()

}
