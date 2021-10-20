package com.conlage.smartshopping.model.data.repository.resultwrapper


sealed class RepositoryResponse{

    data class Success<T>(val response: T): RepositoryResponse()

    data class Failure<T>(val responseError: T): RepositoryResponse()

}
