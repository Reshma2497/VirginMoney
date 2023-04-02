package com.example.virginmoney.data.di

import com.example.virginmoney.data.remote.ApiDetails
import com.example.virginmoney.data.remote.ApiRequest
import com.example.virginmoney.data.repository.Repository
import com.example.virginmoney.data.repository.RepositoryImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides   //returns Gson object
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides   //returns OkHttp client
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level= HttpLoggingInterceptor.Level.BODY
                }
            ).build()
    }

    @Provides   // returns retrofit instance by taking GSON,OkHTTPClient
    fun provideRetrofit(
        gson: Gson,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiDetails.BASEURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    @Provides // returns apiRequest by taking retrofit client as input
    fun provideAPI(
        retrofit: Retrofit
    ): ApiRequest {
        return retrofit.create(ApiRequest::class.java)
    }

    @Provides   //returns repository by taking apiRequest
    fun provideRepository(apiRequest: ApiRequest): Repository {
        return RepositoryImpl(apiRequest)
    }
}