package com.example.mysplashscreen.di

import com.example.mysplashscreen.endpoint.StoreEndPoint
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class EndPointsModules {

    @Singleton
    @Provides
    fun provideStoreEndPoint(retrofit: Retrofit): StoreEndPoint =
        retrofit.create(StoreEndPoint::class.java)
}