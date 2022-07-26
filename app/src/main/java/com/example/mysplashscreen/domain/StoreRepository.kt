package com.example.mysplashscreen.domain

import com.example.mysplashscreen.endpoint.StoreEndPoint
import com.example.mysplashscreen.model.ProductResponse
import retrofit2.Response
import javax.inject.Inject

class StoreRepository @Inject constructor(val endPoint: StoreEndPoint) {

    suspend fun getProducts(): ProductResponse =
        endPoint.getProducts()

}