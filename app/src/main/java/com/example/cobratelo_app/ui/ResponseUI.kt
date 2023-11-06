package com.example.cobratelo_app.ui

sealed class ResponseUI<out T>() {
    data class Success<out T>(val data: T): ResponseUI<T>()
    data class Error(val message: String): ResponseUI<Nothing>()
    data object Loading: ResponseUI<Nothing>()
}
