package com.example.mysplashscreen.endpoint

import com.example.mysplashscreen.model.ProductResponse
import retrofit2.Response
import retrofit2.http.GET

interface StoreEndPoint {

    @GET("pokemon")
    suspend fun getProducts(): ProductResponse

}