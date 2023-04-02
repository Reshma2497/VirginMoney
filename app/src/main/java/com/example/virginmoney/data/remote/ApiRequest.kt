package com.example.virginmoney.data.remote

import com.example.virginmoney.data.model.people.PeopleModel
import com.example.virginmoney.data.model.rooms.RoomsModel
import retrofit2.http.GET

interface ApiRequest {

    @GET(ApiDetails.PEOPLE )
    suspend fun getPeople(): PeopleModel

    @GET(ApiDetails.ROOMS)
    suspend fun getRooms(): RoomsModel

}