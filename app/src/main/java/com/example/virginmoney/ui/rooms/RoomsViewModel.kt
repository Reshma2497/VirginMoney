package com.example.virginmoney.ui.rooms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virginmoney.data.model.errorhandling.ResultOf
import com.example.virginmoney.data.model.rooms.RoomsModel
import com.example.virginmoney.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class RoomsViewModel @Inject constructor(
        val repository: Repository
): ViewModel() {

    val rooms = MutableLiveData<ResultOf<RoomsModel>>()

    fun getRooms() {
        viewModelScope.launch {
            try {
                val result = repository.getRooms()
                rooms.postValue(ResultOf.Success(result))
            } catch (ioe: IOException) {
                rooms.postValue(ResultOf.Failure("[IO] error please retry", ioe))
            } catch (he: HttpException) {
                rooms.postValue(ResultOf.Failure("[HTTP] error please retry", he))
            }
        }
    }
}