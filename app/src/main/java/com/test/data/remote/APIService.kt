package com.test.data.remote


import com.test.data.model.Product
import com.test.data.model.ProductResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("/v1/bonprix/navigation")
    fun getProducts(): Single<Response<ProductResponse>>

    @GET("/v1/bonprix/navigation")
    fun getProduct(): Single<Response<Product>>

}