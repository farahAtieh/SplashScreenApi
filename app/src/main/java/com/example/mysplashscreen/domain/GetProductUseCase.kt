package com.example.mysplashscreen.domain

import com.example.mysplashscreen.util.ApiResponseState
import javax.inject.Inject


class GetProductUseCase @Inject constructor(val repository: StoreRepository) {

    suspend fun getProducts(): ApiResponseState {
        return runCatching {
            repository.getProducts()
        }.map {
            ApiResponseState.Success(it)
        }.getOrElse {
            ApiResponseState.Error(it)
        }

    }
}