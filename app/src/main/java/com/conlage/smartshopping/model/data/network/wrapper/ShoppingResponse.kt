package com.conlage.smartshopping.model.data.network.wrapper

import com.conlage.smartshopping.model.data.network.dto.ResponseError

sealed class ShoppingResponse{

    data class Success<T>(val response: T): ShoppingResponse()

    data class Failure<T>(val responseError: ResponseError): ShoppingResponse()

}
