package com.example.virginmoney.ui.people

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virginmoney.data.model.errorhandling.ResultOf
import com.example.virginmoney.data.model.people.PeopleModel
import com.example.virginmoney.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
        val repository: Repository
): ViewModel() {

    val people=MutableLiveData< ResultOf<PeopleModel> >()

    fun getPeople(){
        viewModelScope.launch {
            try {
                val result = repository.getPeople()
                people.postValue(ResultOf.Success(result))
            }catch(ioe: IOException) {
               people.postValue(ResultOf.Failure("[IO] error please retry", ioe))
            } catch (he: HttpException) {
                people.postValue(ResultOf.Failure("[HTTP] error please retry", he))
            }
        }
    }
}