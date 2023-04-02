package com.example.virginmoney.data.repository

import com.example.virginmoney.data.model.people.PeopleModel
import com.example.virginmoney.data.model.rooms.RoomsModel

interface Repository {

    suspend fun getPeople(): PeopleModel

    suspend fun getRooms(): RoomsModel
}