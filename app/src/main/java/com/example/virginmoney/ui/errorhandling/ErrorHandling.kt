package com.example.virginmoney.ui.errorhandling

import com.example.virginmoney.data.model.errorhandling.ResultOf

object ErrorHandling {
    inline fun <reified T> ResultOf<T>.doIfFailure(callback: (error: String?, throwable: Throwable?) -> Unit) {
        if (this is ResultOf.Failure) {
            callback(message, throwable)
        }
    }

    inline fun <reified T> ResultOf<T>.doIfSuccess(callback: (value: T) -> Unit) {
        if (this is ResultOf.Success) {
            callback(value)
        }
    }
}