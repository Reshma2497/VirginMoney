package com.example.virginmoney.data.repository

import com.example.virginmoney.data.remote.ApiRequest
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    val apiRequest: ApiRequest
) :Repository{
       override suspend fun getPeople()= apiRequest.getPeople()

        override suspend fun getRooms()=apiRequest.getRooms()

}