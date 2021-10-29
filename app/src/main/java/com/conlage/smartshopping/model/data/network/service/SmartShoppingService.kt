package com.conlage.smartshopping.model.data.network.service

import com.conlage.smartshopping.model.data.network.dto.NetworkProduct
import com.conlage.smartshopping.model.data.network.dto.NetworkProductList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface SmartShoppingService {


    @GET("/search/{name}")
    suspend fun getProductListByName(
        @Path("name") productName: String,
        @Query("page") page: Int
    ): NetworkProductList

    @GET("/id/{id}")
    suspend fun getProductDetailsById(
        @Path("id") id: Int
    ): NetworkProduct


    @GET("/barcode/{barcode}")
    suspend fun getProductDetailsByBarcode(
        @Path("barcode") barcode: String
    ): NetworkProduct


}