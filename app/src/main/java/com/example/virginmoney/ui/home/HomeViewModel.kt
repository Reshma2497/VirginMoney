package com.example.virginmoney.ui.home

import android.text.SpannableString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Welcome to VIRGIN MONEY!.\n " +
                "In this app you will find details about people and rooms. please click respective tabs to explore more. "
    }
    val text: LiveData<String> = _text

}