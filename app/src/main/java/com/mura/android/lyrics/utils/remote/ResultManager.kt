package com.mura.android.lyrics.utils.remote

sealed class ResultManager<out T : Any> {
    data class Success<T : Any>(val items: T?) : ResultManager<T>()
    data class Error(val errorCode: Int, val errorMessage: String) : ResultManager<Nothing>()
    data class Exception(val exception: kotlin.Exception) : ResultManager<Nothing>()
}