package com.conlage.smartshopping.model.data.usecase.wrapper

sealed class UseCaseResult{

    data class Response<T>(val value: T): UseCaseResult()

    data class Error<T>(val throwable: T?): UseCaseResult()

}
