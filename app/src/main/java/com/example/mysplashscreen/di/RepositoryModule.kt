package com.example.mysplashscreen.di

import com.example.mysplashscreen.endpoint.StoreEndPoint
import com.example.mysplashscreen.domain.StoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideCountryNewsRepository(storeEndPoint: StoreEndPoint): StoreRepository =
        StoreRepository(storeEndPoint)
}