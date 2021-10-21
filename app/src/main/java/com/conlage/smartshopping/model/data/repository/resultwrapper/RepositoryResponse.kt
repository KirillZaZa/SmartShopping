package com.conlage.smartshopping.model.data.repository.resultwrapper


sealed class RepositoryResponse<T>{

    data class Success<T>(val response: T): RepositoryResponse<T>()

    data class Failure<T>(val responseError: Throwable?): RepositoryResponse<T>()

}
